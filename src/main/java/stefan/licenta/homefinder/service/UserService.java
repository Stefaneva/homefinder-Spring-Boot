package stefan.licenta.homefinder.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import stefan.licenta.homefinder.dao.AdImageRepository;
import stefan.licenta.homefinder.dao.AdRepository;
import stefan.licenta.homefinder.dao.UserRepository;
import stefan.licenta.homefinder.dto.*;
import stefan.licenta.homefinder.entity.Ad;
import stefan.licenta.homefinder.entity.AdImage;
import stefan.licenta.homefinder.entity.User;
import stefan.licenta.homefinder.entity.UserType;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class UserService {

    private UserRepository userRepository;
    private AdRepository adRepository;
    private PasswordEncoder passwordEncoder;
    private AdImageRepository adImageRepository;
    private List<MultipartFile> uploadFiles;

    public UserService(UserRepository userRepository, AdRepository adRepository, PasswordEncoder passwordEncoder, AdImageRepository adImageRepository) {
        this.userRepository = userRepository;
        this.adRepository = adRepository;
        this.passwordEncoder = passwordEncoder;
        this.adImageRepository = adImageRepository;
    }

    public void setUploadFiles(List<MultipartFile> files) {
        uploadFiles = files;
    }

    public User transformUserDto(UserRegistrationDto userRegistrationDto){
        User user = null;
        if(userRegistrationDto != null && userRepository.findByEmail(userRegistrationDto.getEmail()) == null) {
            user = new User();
            user.setUsername(userRegistrationDto.getUsername());
            user.setEmail(userRegistrationDto.getEmail());
            user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
            user.setPhone(userRegistrationDto.getPhoneNumber());
            user.setEnabled(true);
            String s = userRegistrationDto.getUserType();
            if(s.equals("Cumparator/Chirias"))
                user.setType(UserType.USER);
            else
                user.setType(UserType.AGENT_IMOBILIAR);
            user.setLastPasswordResetDate(this.getCurrentTime());
        }
        return user;
    }

    public void registerNewUser(UserRegistrationDto userRegistrationDto){
        userRepository.save(this.transformUserDto(userRegistrationDto));
    }

    public Timestamp getCurrentTime(){
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    public UserDto getUserData (EmailDto emailDto) {
        UserDtoTransformer userDtoTransformer = new UserDtoTransformer();
        User user = userRepository.findByEmail(emailDto.getEmail());
        user.setLastLoginDate(user.getCurrentLoginDate());
        user.setCurrentLoginDate(new Date());
        userRepository.save(user);
        return userDtoTransformer.transform(user);
    }

    public void saveAdInfo(NewAdDto newAdDto) throws IOException {
        Ad newAd = new Ad();
        newAd.setTitle(newAdDto.getTitle());
        newAd.setDescription(newAdDto.getDescription());
        newAd.setAdTranzactionType(newAdDto.getAdType());
        newAd.setAdType(newAdDto.getAdItemType());
        newAd.setPrice(newAdDto.getPrice());
        newAd.setRoomNumber(newAdDto.getRooms());
        newAd.setSurface(newAdDto.getSurface());
        newAd.setUserId(userRepository.findByEmail(newAdDto.getUserEmail()));
        newAd.setLat(newAdDto.getLat());
        newAd.setLng(newAdDto.getLng());
        if(newAdDto.getUploadFiles().size() > 0) {
            for(MultipartFile file : newAdDto.getUploadFiles()) {
                AdImage adImage = new AdImage();
                byte[] image = file.getBytes();
                adImage.setImage(image);
                adImage.setAdId(newAd);
                adImageRepository.save(adImage);
            }
        }
    }

    public List<AdDto> getAllAdsWithFirstImage() {
        List<AdDto> adDtoList = new ArrayList<>();
        List<Ad> adList = adRepository.findAll();
        for(Ad i : adList) {
            System.out.println(i.getAdId());
            AdDto adDto = new AdDto();
            AdImage adImage = adImageRepository.findFirstByAdId(i);
            if(adImage != null) {
                String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(adImage.getImage());
                adDto.setImage(encodeImage);
            }
            adDto.setTitle(i.getTitle());
            adDto.setAdType(i.getAdTranzactionType());
            adDto.setAdItemType(i.getAdType());
            adDto.setDescription(i.getDescription());
            adDto.setLat(i.getLat());
            adDto.setLng(i.getLng());
            adDto.setPrice(i.getPrice());
            adDto.setSurface(i.getSurface());
            adDto.setRooms(i.getRoomNumber());
            adDto.setUserEmail(i.getUserId().getEmail());
            adDtoList.add(adDto);
        }
        return adDtoList;
    }
}
