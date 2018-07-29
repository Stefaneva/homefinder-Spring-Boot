package stefan.licenta.homefinder.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import stefan.licenta.homefinder.dao.*;
import stefan.licenta.homefinder.dto.*;
import stefan.licenta.homefinder.entity.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private AdRepository adRepository;
    private PasswordEncoder passwordEncoder;
    private AdImageRepository adImageRepository;
    private List<MultipartFile> uploadFiles;
    private FavoriteRepository favoriteRepository;
    private ReviewRepository reviewRepository;
    private EventRepository eventRepository;
    private EventDtoTransformer eventDtoTransformer;

    public UserService(UserRepository userRepository, AdRepository adRepository,
                       PasswordEncoder passwordEncoder, AdImageRepository adImageRepository,
                       FavoriteRepository favoriteRepository, ReviewRepository reviewRepository,
                       EventRepository eventRepository, EventDtoTransformer eventDtoTransformer) {
        this.userRepository = userRepository;
        this.adRepository = adRepository;
        this.passwordEncoder = passwordEncoder;
        this.adImageRepository = adImageRepository;
        this.favoriteRepository = favoriteRepository;
        this.reviewRepository = reviewRepository;
        this.eventRepository = eventRepository;
        this.eventDtoTransformer = eventDtoTransformer;
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

    public void updateUserData(UserDtoUpdate userDtoUpdate) {
        User user = userRepository.findByEmail(userDtoUpdate.getMail());
        user.setPassword(passwordEncoder.encode(userDtoUpdate.getPassword()));
        user.setPhone(userDtoUpdate.getPhone());
        userRepository.save(user);
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
        newAd.setDate(new Date());
        newAd.setSurface(newAdDto.getSurface());
        newAd.setAreaSurface(newAdDto.getAreaSurface());
        newAd.setFloorLevel(newAdDto.getFloorLevel());
        newAd.setComfort(newAdDto.getComfort());
        newAd.setPartitioning(newAdDto.getPartitioning());
        newAd.setFurnished(newAdDto.getFurnished());
        newAd.setYearBuilt(newAdDto.getYearBuilt());
        newAd.setLocation(newAdDto.getLocation());
        if(newAdDto.getUploadFiles().size() > 0) {
            this.saveImages(newAdDto.getUploadFiles(), newAd);
        }
    }

    private void saveImages(List<MultipartFile> images, Ad adId) {
        for(MultipartFile file : images) {
            AdImage adImage = new AdImage();
            adImage.setAdId(adId);
            try {
                byte[] image = file.getBytes();
                adImage.setImage(image);
                adImageRepository.save(adImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void replaceImages(Long adId, List<MultipartFile> images) {
        Ad ad = adRepository.findById(adId).get();
        adImageRepository.deleteAllByAdId(ad.getAdId());
        this.saveImages(images, ad);
    }

    public void updateAdInfo(AdDto adDto) {
        Ad ad = adRepository.findById(adDto.getId()).get();
        ad.setTitle(adDto.getTitle());
        ad.setDescription(adDto.getDescription());
        ad.setAdTranzactionType(adDto.getAdType());
        ad.setAdType(adDto.getAdItemType());
        ad.setPrice(adDto.getPrice());
        ad.setRoomNumber(adDto.getRooms());
        ad.setSurface(adDto.getSurface());
        ad.setUserId(userRepository.findByEmail(adDto.getUserEmail()));
        ad.setLat(adDto.getLat());
        ad.setLng(adDto.getLng());
        ad.setSurface(adDto.getSurface());
        ad.setAreaSurface(adDto.getAreaSurface());
        ad.setFloorLevel(adDto.getFloorLevel());
        ad.setComfort(adDto.getComfort());
        ad.setPartitioning(adDto.getPartitioning());
        ad.setFurnished(adDto.getFurnished());
        ad.setYearBuilt(adDto.getYearBuilt());
        adRepository.save(ad);
    }

    public List<AdDto> getAllAdsWithFirstImage() {
        List<AdDto> adDtoList = new ArrayList<>();
        List<Ad> adList = adRepository.findAllByOrderByDateDesc();
        for(Ad i : adList) {
            System.out.println(i.getAdId());
            AdDto adDto = new AdDto();
            AdImage adImage = adImageRepository.findFirstByAdId(i);
            if(adImage != null) {
                String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(adImage.getImage());
                adDto.setImage(encodeImage);
            }
            adDto.setId(i.getAdId());
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
            adDto.setPartitioning(i.getPartitioning());
            adDto.setComfort(i.getComfort());
            adDto.setFurnished(i.getFurnished());
            adDto.setFloorLevel(i.getFloorLevel());
            adDto.setAreaSurface(i.getAreaSurface());
            adDto.setYearBuilt(i.getYearBuilt());
            adDto.setLocation(i.getLocation());
            adDtoList.add(adDto);
        }
        return adDtoList;
    }

    public AdDto adDtoBuilder(Ad i) {
        AdDto adDto = new AdDto();
        AdImage adImage = adImageRepository.findFirstByAdId(i);
        if(adImage != null) {
            String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(adImage.getImage());
            adDto.setImage(encodeImage);
        }
        adDto.setId(i.getAdId());
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
        adDto.setPartitioning(i.getPartitioning());
        adDto.setComfort(i.getComfort());
        adDto.setFurnished(i.getFurnished());
        adDto.setFloorLevel(i.getFloorLevel());
        adDto.setAreaSurface(i.getAreaSurface());
        adDto.setYearBuilt(i.getYearBuilt());
        return adDto;
    }

    public List<AdDto> getUserAds(EmailDto emailDto) {
        List<AdDto> adDtoList = new ArrayList<>();
        User currentUser = userRepository.findByEmail(emailDto.getEmail());
        List<Ad> adList = adRepository.findAllByUserIdOrderByDateDesc(currentUser);
        for(Ad i : adList) {
            adDtoList.add(this.adDtoBuilder(i));
        }
        return adDtoList;
    }

    public AdDetailsDto getAdInfo(Long adId) {
        AdDetailsDto adDetailsDto = new AdDetailsDto();
        Ad ad = adRepository.findById(adId).get();
        adDetailsDto.setId(ad.getAdId());
        adDetailsDto.setTitle(ad.getTitle());
        adDetailsDto.setDescription(ad.getDescription());
        adDetailsDto.setAdType(ad.getAdTranzactionType());
        adDetailsDto.setAdItemType(ad.getAdType());
        adDetailsDto.setPrice(ad.getPrice());
        adDetailsDto.setRooms(ad.getRoomNumber());
        adDetailsDto.setSurface(ad.getSurface());
        adDetailsDto.setLat(ad.getLat());
        adDetailsDto.setLng(ad.getLng());
        adDetailsDto.setPartitioning(ad.getPartitioning());
        adDetailsDto.setComfort(ad.getComfort());
        adDetailsDto.setFurnished(ad.getFurnished());
        adDetailsDto.setFloorLevel(ad.getFloorLevel());
        adDetailsDto.setAreaSurface(ad.getAreaSurface());
        adDetailsDto.setYearBuilt(ad.getYearBuilt());
        UserDto newUserDto = new UserDto();
        newUserDto.setName(ad.getUserId().getUsername());
        newUserDto.setMail(ad.getUserId().getEmail());
        newUserDto.setPhone(ad.getUserId().getPhone());
        newUserDto.setUserType(ad.getUserId().getType());
        newUserDto.setLastLoginDate(ad.getUserId().getLastLoginDate().toString());
        adDetailsDto.setUserDetails(newUserDto);
        return adDetailsDto;
    }

    public List<String> getAdImages(Long adId) {
        Ad ad = adRepository.findById(adId).get();
        List<AdImage> images = adImageRepository.findAllByAdId(ad);
        List<String> encodedImages = new ArrayList<>();
        for(AdImage adImage: images) {
            String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(adImage.getImage());
            encodedImages.add(encodeImage);
        }
        return encodedImages;
    }

    public void deleteAdById(Long adId) {
        adRepository.deleteById(adId);
    }

    public List<AdDto> getAllFavorites(String userEmail) {
        List<AdDto> adDtoList = new ArrayList<>();
        User user = userRepository.findByEmail(userEmail);
        List<Ad> adList = new ArrayList<>();
        user.getFavorites().forEach(favorite -> {
            System.out.println(favorite.getAd().getAdId());
            adList.add(favorite.getAd());
        });
        adList.forEach(ad -> {
            adDtoList.add(this.adDtoBuilder(ad));
        });
        return adDtoList;
    }

    public void saveFavorite(FavoriteDto favoriteDto) {
        Favorite favorite = new Favorite();
        favorite.setAd(adRepository.findById(favoriteDto.getAdId()).get());
        favorite.setUser(userRepository.findByEmail(favoriteDto.getUserEmail()));
        favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Long favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }

    public List<ReviewDtoRequest> getAdReviews(Long adId) {
        List<Review> reviewList = reviewRepository.findAllByAd(adRepository.findById(adId).get());
        List<ReviewDtoRequest> reviewDtoRequestList = new ArrayList<>();
        for (Review review : reviewList) {
            ReviewDtoRequest reviewDtoRequest = ReviewDtoRequest.builder().idReview(review.getId())
                    .comment(review.getComment())
                    .rating(review.getRating())
                    .like(review.getRating())
                    .username(review.getUser().getUsername())
                    .userType(review.getUser().getType())
                    .mail(review.getUser().getEmail())
                    .date(review.getDate())
                    .build();
            reviewDtoRequestList.add(reviewDtoRequest);
        }
        return  reviewDtoRequestList;
    }

    public void saveAdReview(ReviewDtoResponse reviewDtoResponse) {
        Review review = Review.builder().ad(adRepository.findById(reviewDtoResponse.getAdId()).get())
                                        .user(userRepository.findByEmail(reviewDtoResponse.getMail()))
                                        .comment(reviewDtoResponse.getComment())
                                        .rating(reviewDtoResponse.getRating())
                                        .like(reviewDtoResponse.getLike())
                                        .date(this.getCurrentTime())
                                        .build();
        reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.delete(reviewRepository.findById(reviewId).get());
    }

    public void updateReview(ReviewDtoResponse reviewDtoResponse) {
        Review review = reviewRepository.findById(reviewDtoResponse.getIdReview()).get();
        review.setComment(reviewDtoResponse.getComment());
        review.setRating(reviewDtoResponse.getRating());
        review.setLike(reviewDtoResponse.getLike());
        reviewRepository.save(review);
    }

    public List<String> getUserEmails() {
        return userRepository.getAllUsersEmail();
    }

    public void saveEvent(EventDtoDate eventDto) throws ParseException {
        TimeZone.setDefault(TimeZone.getTimeZone("EEST"));
        System.out.println(eventDto);
        Event event = new Event();
        event.setAd(adRepository.findById(eventDto.getAdId()).get());
        event.setUser(userRepository.findByEmail(eventDto.getUserEmail()));
        event.setStatus(eventDto.getStatus());
        event.setMessage(eventDto.getMessage());
        event.setStartDate(eventDto.getStartDate());
        event.setEndDate(eventDto.getEndDate());
        eventRepository.save(event);
    }

    public List<EventDto> getUserEvents(EmailDto emailDto) {
        return eventDtoTransformer.transformList(userRepository.findByEmail(emailDto.getEmail()).getEvents());
    }

    public List<EventDto> getAdEvents(Long adId) {
        return eventDtoTransformer.transformList(adRepository.findById(adId).get().getEvents());
    }

    public void deleteEvent(EventDtoDate eventDto) {
        eventRepository.deleteByUserAndAd(
                userRepository.findByEmail(eventDto.getUserEmail()),adRepository.findById(eventDto.getAdId()).get());
    }

    public void updateEvent(EventDtoDate eventDto) throws ParseException {
        TimeZone.setDefault(TimeZone.getTimeZone("EEST"));
        System.out.println(eventDto);
        Event event = eventRepository.findByUserAndAd(
                userRepository.findByEmail(eventDto.getUserEmail()),
                adRepository.findById(eventDto.getAdId()).get());
        event.setStatus(eventDto.getStatus());
        event.setMessage(eventDto.getMessage());
        event.setStartDate(eventDto.getStartDate());
        event.setEndDate(eventDto.getEndDate());
        eventRepository.save(event);
    }
}
