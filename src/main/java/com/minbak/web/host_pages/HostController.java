package com.minbak.web.host_pages;

import com.minbak.web.spring_security.CustomUserDetails;
import org.apache.catalina.Host;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import com.minbak.web.host_pages.dto.HostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/host")
@SessionAttributes("hostDto")
public class HostController {
    @Autowired
    private HostService hostService;

    @ModelAttribute("hostDto")
    public HostDto hostDto() {
        return new HostDto(); // 세션에 없으면 새로운 객체 반환
    }

    @GetMapping("/create-rooms")
    public String createRooms(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @ModelAttribute("hostDto") HostDto hostDto,
                              Model model) {
        // userId가 설정되지 않았다면 로그인된 사용자의 ID(1번) 설정
        if(userDetails != null){
            hostDto.setUserId(userDetails.getUserId());
            System.out.println("로그인한 userId: " + userDetails.getUserId());
        }

        // userName이 아직 설정되지 않았다면 DB에서 가져오기
        if (hostDto.getUserName() == null) {
            String userName = hostService.getUserName(hostDto.getUserId());
            hostDto.setUserName(userName);
            System.out.println("DB에서 가져온 userName: " + userName); // 로그 확인
        }

        model.addAttribute("name", hostDto.getUserName());
        return "host-pages/rooms_create";
    }

    @GetMapping("/overview")
    public String overview(){
        return "host-pages/overview";
    }

    @GetMapping("/place")
    public String place(){
        return "host-pages/place";
    }
    // 숙소 유형 선택 페이지
    @GetMapping("/type")
    public String roomsType(@ModelAttribute("hostDto") HostDto hostDto,Model model){
        model.addAttribute("selectedType",hostDto.getBuildingType());
        return "host-pages/type";
    }
    // 숙소 유형 저장 후 다음 페이지 이동
    @PostMapping("/type/save")
    @ResponseBody // AJAX 응답
    public String saveBuildingType(@ModelAttribute("hostDto") HostDto hostDto, @RequestParam("buildingType") String buildingType) {
        hostDto.setBuildingType(buildingType);
        System.out.println("🏡 선택한 숙소 유형 저장: " + hostDto.getBuildingType()); // ✅ 콘솔 로그 확인
        return "success"; // AJAX 요청 완료 응답
    }


    @GetMapping("/location")
    public String roomLocation(@ModelAttribute("hostDto") HostDto hostDto,Model model){
        model.addAttribute("selectedAddress", hostDto.getAddress());
        model.addAttribute("latitude", hostDto.getLatitude());
        model.addAttribute("longitude", hostDto.getLongitude());
        return "host-pages/location";
    }
    @PostMapping("/location/save")
    public String saveLocation(@ModelAttribute("hostDto") HostDto hostDto,
                               @RequestParam("province") String province,
                               @RequestParam("city") String city,
                               @RequestParam("district") String district,
                               @RequestParam("roadAddress") String roadAddress,
                               @RequestParam(value = "buildingName", required = false) String buildingName,
                               @RequestParam(value = "postalCode", required = false) String postalCode,
                               @RequestParam("latitude") Double latitude,
                               @RequestParam("longitude") Double longitude) {

        // 🔹 주소를 하나의 문자열로 조합하여 저장
        String fullAddress = String.format("%s %s %s %s %s %s",
                province, city, district, roadAddress,
                (buildingName != null && !buildingName.isEmpty()) ? buildingName : "",
                (postalCode != null && !postalCode.isEmpty()) ? "(우편번호: " + postalCode + ")" : "");

        // 🔹 `HostDto`에 값 저장
        hostDto.setAddress(fullAddress.trim()); // 불필요한 공백 제거 후 저장
        hostDto.setLatitude(latitude);
        hostDto.setLongitude(longitude);

        // ✅ 콘솔 로그로 값 확인
        System.out.println("📌 [저장할 주소 데이터]");
        System.out.println("도/특별-광역시: " + province);
        System.out.println("도시: " + city);
        System.out.println("군/구: " + district);
        System.out.println("도로명 주소: " + roadAddress);
        System.out.println("건물명: " + buildingName);
        System.out.println("우편번호: " + postalCode);
        System.out.println("최종 저장할 주소: " + fullAddress);
        System.out.println("위도: " + latitude);
        System.out.println("경도: " + longitude);

        return "redirect:/host/floor-plan"; // 다음 페이지로 이동
    }

    @GetMapping("/floor-plan")
    public String floorPlan(){
        return "host-pages/floor-plan";
    }
    @PostMapping("/floor-plan/save")
    public String saveFloorPlan(@ModelAttribute("hostDto") HostDto hostDto,
                                @RequestParam("maxGuests") Integer maxGuests,
                                @RequestParam("bedrooms") Integer bedrooms,
                                @RequestParam("beds") Integer beds,
                                @RequestParam("bathrooms") Integer bathrooms){

        hostDto.setMaxGuests(maxGuests);
        hostDto.setBedrooms(bedrooms);
        hostDto.setBeds(beds);
        hostDto.setBathrooms(bathrooms);
        // ✅ 콘솔 로그 확인
        System.out.println("📌 [저장된 숙소 기본 정보]");
        System.out.println("게스트 수: " + hostDto.getMaxGuests());
        System.out.println("침실 수: " + hostDto.getBedrooms());
        System.out.println("침대 수: " + hostDto.getBeds());
        System.out.println("욕실 수: " + hostDto.getBathrooms());

        return "redirect:/host/charm"; // ✅ 다음 페이지로 이동
    }

    @GetMapping("/charm")
    public String charm(){
        return "host-pages/accommodation-charm";
    }

    @GetMapping("/option")
    public String roomsOption(){
        return "host-pages/option";
    }
    @PostMapping("/option/save")
    public String saveCharm(@ModelAttribute("hostDto") HostDto hostDto,
                            @RequestParam("optionIds") String optionIds) {

        // ✅ 옵션 ID 리스트 저장 (Integer 변환)
        List<Integer> selectedOptions = Arrays.stream(optionIds.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        hostDto.setOptionIds(selectedOptions);

        // ✅ 콘솔 로그 확인
        System.out.println("📌 [저장된 숙소 옵션]");
        selectedOptions.forEach(option -> System.out.println("선택한 옵션 ID: " + option));

        return "redirect:/host/photos"; // ✅ 다음 페이지로 이동
    }


    @GetMapping("/photos")
    public String photos(){
        return "host-pages/photos";
    }
    @PostMapping("/photos/save")
    public String savePhotos(@ModelAttribute("hostDto")HostDto hostDto,
                             @RequestParam("photos")MultipartFile[] photos){
        List<String> imageUrls = new ArrayList<>();
        for(MultipartFile photo : photos){
            if(!photo.isEmpty()){
                String fileUrl = "/uploads/" + photo.getOriginalFilename();
                imageUrls.add(fileUrl);
            }
        }
        hostDto.setImageUrls(imageUrls);

        System.out.println("📌 [업로드된 이미지 목록]");
        imageUrls.forEach(url -> System.out.println("✔ " + url));

        return "redirect:/host/roomsName";
    }

    @GetMapping("/roomsName")
    public String roomsName(){
        return "host-pages/roomsName";
    }

    @GetMapping("/title")
    public String roomsTitle(){
        return "host-pages/title";
    }

    @GetMapping("/description")
    public String description(){
        return "host-pages/description";
    }

    @GetMapping("useGuide")
    public String roomsUseGuide(){
        return "host-pages/useGuide";
    }

    @GetMapping("/finish-setup")
    public String finish(){
        return "host-pages/finish-setup";
    }

    @GetMapping("/price")
    public String roomsPrice(){
        return "host-pages/price";
    }

    @GetMapping("/receipt")
    public String receipt(){
        return "host-pages/receipt";
    }

    @GetMapping("/publish")
    public String publish(){
        return "host-pages/publish";
    }


}
