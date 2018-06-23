package stefan.licenta.homefinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stefan.licenta.homefinder.dao.AdImageRepository;
import stefan.licenta.homefinder.dao.AdRepository;
import stefan.licenta.homefinder.dao.UserRepository;
import stefan.licenta.homefinder.dto.*;
import stefan.licenta.homefinder.entity.Ad;
import stefan.licenta.homefinder.entity.AdImage;
import stefan.licenta.homefinder.entity.User;
import stefan.licenta.homefinder.service.UserService;

import java.io.IOException;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    private AdImageRepository adImageRepository;
    @Autowired
    private AdRepository adRepository;
    private UserService userService;
    private List<Map<String, String>> mapList = new ArrayList<>();

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public void userRegistration(@RequestBody UserRegistrationDto userRegistrationDto){
        System.out.println(userRegistrationDto);
        userService.registerNewUser(userRegistrationDto);
    }

    @PostMapping("/getUserData")
    public UserDto getUserData(@RequestBody EmailDto emailDto) {
        return userService.getUserData(emailDto);
    }

    @PostMapping(value="/newAdImages" , consumes = {"multipart/form-data"})
    public void saveNewAdImages(@RequestParam("fileUpload") List<MultipartFile> images,
                                @RequestParam("title") String title,
                                @RequestParam("description") String description,
                                @RequestParam("adItemType") String adItemType,
                                @RequestParam("adType") String adType,
                                @RequestParam("price") String price,
                                @RequestParam("rooms") String rooms,
                                @RequestParam("surface") String surface,
                                @RequestParam("lat") String lat,
                                @RequestParam("lng") String lng,
                                @RequestParam("userEmail") String userEmail) throws IOException {

        userService.setUploadFiles(images);
        NewAdDto newAdDto = new NewAdDto();
        newAdDto.setTitle(title);
        newAdDto.setDescription(description);
        newAdDto.setAdItemType(adItemType);
        newAdDto.setAdType(adType);
        newAdDto.setPrice(Integer.parseInt(price));
        newAdDto.setRooms(Integer.parseInt(rooms));
        newAdDto.setSurface(Integer.parseInt(surface));
        newAdDto.setLat(Double.parseDouble(lat));
        newAdDto.setLng(Double.parseDouble(lng));
        newAdDto.setUserEmail(userEmail);
        newAdDto.setUploadFiles(images);
        userService.saveAdInfo(newAdDto);
    }

    @PostMapping("/adsWithImages")
    public List<AdDto> getAllAdsWithFirstImage() {
        return userService.getAllAdsWithFirstImage();
    }

    @PostMapping("/getUserAds")
    public List<AdDto> getUserAds(@RequestBody EmailDto userEmail) {
        System.out.println(userEmail.getEmail());
        return userService.getUserAds(userEmail);
    }

    @PostMapping("/getAdInfo")
    public AdDetailsDto getAdInfo(@RequestBody Long adId) {
        return userService.getAdInfo(adId);
    }

    @PostMapping("/getAdImages")
    public List<String> getAdImages(@RequestBody Long adId) {
        return userService.getAdImages(adId);
    }
}
