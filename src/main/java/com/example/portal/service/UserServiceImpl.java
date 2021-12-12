package com.example.portal.service;


import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

import com.example.portal.info.LoginInfo;
import com.example.portal.entity.User;
import com.example.portal.repository.AddressRepository;
import com.example.portal.repository.UserRepository;
import com.example.portal.utils.GeneralUtil;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.example.portal.utils.EncodingPassword.ncoder;
import static com.example.portal.utils.GeneralUtil.*;

//@NoArgsConstructor
//@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository){
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public ResponseEntity<String> createUser(User user){
        String randomusername = "", randompassword = "";
        if(user.getName().isEmpty() || spellCheck(user.getName())) return new ResponseEntity<>("Name cannot have special character", HttpStatus.BAD_REQUEST);
        else if(user.getAddress().getLine_one().isEmpty()) return new ResponseEntity<>("Line one in Address is Required",HttpStatus.BAD_REQUEST);
        else if(user.getAddress().getCity().isEmpty()) return new ResponseEntity<>("City Field is Required",HttpStatus.BAD_REQUEST);
        else if(user.getAddress().getPincode().length()!=6) return new ResponseEntity<>("Pin Code is Invalid",HttpStatus.BAD_REQUEST);
        else if(user.getAddress().getState().isEmpty()) return new ResponseEntity<>("State Field is Required",HttpStatus.BAD_REQUEST);
        else if(user.getEmergency_number().isEmpty() || user.getEmergency_number().length()!=10) return new ResponseEntity<>("Emergency Number is Required",HttpStatus.BAD_REQUEST);
        else if(new StringTokenizer(user.getSkills(), ",").countTokens() < 2) return new ResponseEntity<>("Minimum 2 skills are required",HttpStatus.BAD_REQUEST);
        else if(!user.getUsername().isEmpty() && userRepository.findByUsername(user.getUsername())!=null) return new ResponseEntity<>("User Name Already Taken",HttpStatus.BAD_REQUEST);
        if(user.getUsername().isEmpty()){
            randomusername = genRandomUsername();
            while(userRepository.findByUsername(randomusername)!=null) randomusername = genRandomUsername();
            user.setUsername(randomusername);
        }
        else if(!pwdCheck(user.getPassword()) && !user.getPassword().isEmpty()) return new ResponseEntity<>("Weak Password",HttpStatus.BAD_REQUEST);
        if(user.getPassword().isEmpty()){
            randompassword = genRandomPassword();
            user.setPassword(randompassword);
            //user.setPassword(new BCryptPasswordEncoder().encode(randompassword));
        }
        if(user.getAddress().getCurrent_line_one().equals("")) user.getAddress().setCurrent_line_one(user.getAddress().getLine_one());
        if(user.getAddress().getCurrent_line_two().equals("")) user.getAddress().setCurrent_line_two(user.getAddress().getLine_two());
        if(user.getAddress().getCurrent_city().equals("")) user.getAddress().setCurrent_city(user.getAddress().getCity());
        if(user.getAddress().getCurrent_pincode().equals("")) user.getAddress().setCurrent_pincode(user.getAddress().getPincode());
        if(user.getAddress().getCurrent_state().equals("")) user.getAddress().setCurrent_state(user.getAddress().getState());

        //encrypt
        user.setPassword(ncoder(user.getPassword()));


        userRepository.save(user);


        if(!randomusername.equals("") && !randompassword.equals("")) {
            return new ResponseEntity<>("Registered Succesfully" + '\n' + "Username: " +
                    randomusername + '\n' + "Password: " + randompassword, HttpStatus.ACCEPTED);
        }
        else if(!randomusername.equals("")){
            return new ResponseEntity<>("Registered Succesfully" + '\n' + "Username: " +
                    randomusername , HttpStatus.ACCEPTED);
        }
        else if(!randompassword.equals("")){
            return new ResponseEntity<>("Registered Succesfully" + '\n' + "Password: " + randompassword, HttpStatus.ACCEPTED);
        }
        else return new ResponseEntity<>("Registered Succesfully", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> checkLogin(LoginInfo login){
        User user = userRepository.findByUsername(login.getUsername());

        //System.out.println(login.getPassword());
        if(user == null){
            return new ResponseEntity<>("username doesn't exist",HttpStatus.FORBIDDEN);
        }
        else if(!(user.getPassword().equals(login.getPassword()))){
            return new ResponseEntity<>("Password doesnt Match",HttpStatus.FORBIDDEN);
        }
        else{
            return new ResponseEntity<>(tokenGenerator(15),HttpStatus.OK);
        }
        //return user.toString();
    }

//    public String returnCenters(String USERNAME){
//        User user = userRepository.findByUsername(USERNAME);
//        String pincode = user.getAddress().getPincode();
//        //System.out.println(pincode);
//        String date = getDate();
//        final String uri = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode="+pincode+"&date="+date;
////        try{
////            RestTemplate restTemplate = new RestTemplate();
////            String content = restTemplate.getForObject(uri, String.class);
////            //System.out.println(content);
////            Files.write(Paths.get("./apiResponse.txt"),content.getBytes(StandardCharsets.UTF_8));
////            String result = Files.readString(Paths.get("apiResponse.txt"), StandardCharsets.US_ASCII);
////
////            JSONObject json = new JSONObject(result.toString());
////            JSONArray arr = json.getJSONArray("sessions");
////            HashMap<String,String> name_fee = new HashMap<>();
////            for(int i=0;i<arr.length();i++){
////                JSONObject tmp=arr.getJSONObject(i);
////                name_fee.put(tmp.get("name").toString(),tmp.get("fee_type").toString());
////            }
////            return name_fee.toString();
////        }
////        catch(Exception e){
////            return e.getMessage();
////        }
//        return "Hello";
//    }
    public JSONObject returnCenters1(String USERNAME){
        User user = userRepository.findByUsername(USERNAME);
        String pincode = user.getAddress().getPincode();
        //System.out.println(pincode);
        String date = getDate();
        //final String uri = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode="+pincode+"&date="+date;
        String inline = "";
        try {
            URL url = new URL("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode=" + pincode + "&date=" + date);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) throw new RuntimeException("HttpResponseCode: " + responseCode);
            else {
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                System.out.println("\nJSON data in string format");
                System.out.println(inline);
                sc.close();
            }
            JSONParser parser = new JSONParser();
            org.json.simple.JSONObject obj = (org.json.simple.JSONObject) parser.parse(inline);
//          JSONArray jsonArray = (JSONArray) obj.get("sessions");
            return obj;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        org.json.simple.JSONObject obj1 = new org.json.simple.JSONObject();
        return obj1;
    }

}
