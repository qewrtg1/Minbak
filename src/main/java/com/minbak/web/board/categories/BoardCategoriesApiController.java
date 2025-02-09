package com.minbak.web.board.categories;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/board/category")
public class BoardCategoriesApiController {

    @Autowired
    BoardCategoriesService boardCategoriesService;

    @GetMapping
    //카데고리를 순서대로 가져오기
    public List<BoardCategoryDto> getOrderedCategories() {
        List<BoardCategoryDto> boardCategories = boardCategoriesService.findOrderedCategories();
        return boardCategories;
    }

    @PostMapping("/reorder")
    //카테고리 순서대로 재배열
    public ResponseEntity<?> reOrderCategories(@RequestBody Map<String, List<Integer>> request){

        List<Integer> newOrder = request.get("order");

        // 카테고리 순서 변경 로직
        boardCategoriesService.updateCategoryOrder(newOrder);

        // 순서 변경이 완료되었음을 알리는 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "카테고리 순서가 변경되었습니다.");

        //response와 200반환
        return ResponseEntity.ok(response);

    }

    @PutMapping("/{id}")
    //카테고리 수정하기
    public ResponseEntity<?> updateCategory(@PathVariable("id") int id, @RequestBody BoardCategoryDto boardCategoryDto) {
        try {
            // 수정하려는 카테고리 ID와 새로운 카테고리 이름을 다음 메서드에 전달
            boardCategoriesService.updateCategory(id, boardCategoryDto.getName());

            Map<String, String> response = new HashMap<>();
            response.put("message", "카테고리 업데이트 완료");
            
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {

            Map<String, String> response = new HashMap<>();
            response.put("message", "카테고리 업데이트 실패");

            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") int id) {
        // 수정하려는 카테고리 ID와 새로운 카테고리 이름을 서비스로 전달
        try {
            // 카테고리 업데이트 서비스 호출
            boardCategoriesService.deleteCategory(id);

            Map<String, String> response = new HashMap<>();
            response.put("message", "카테고리 삭제 완료");

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {

            Map<String, String> response = new HashMap<>();
            response.put("message", "카테고리 삭제 실패");

            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/create")
    //@Valid 유효성검사 어노테이션 추가
    //BindingResult bindingResult 검사 메시지를 받아오는 객체 추가, @Valid랑 세트라고 보면 됨.
    //binding 뜻 - 결합, 연결
    public ResponseEntity<?> createCategory(@RequestBody @Valid BoardCategoryDto boardCategoryDto, BindingResult bindingResult) {

        try {

            //맵객체 만들기, json정보전달은 모두 이 형태라고 보면 됨
            Map<String, String> response = new HashMap<>();

            //에러가 있으면,
            if(bindingResult.hasErrors()){

                //bindingResilt의 FieldErrors() 는, 해당필드와 메시지를 갖고있는 배열객체이다.
                bindingResult.getFieldErrors().forEach(error->{
                    //있다면 여기서는 categoryName일 것,
                    String field = error.getField();
                    //위 필드의 에러메시지.(필드에 적혀있음)
                    String message = error.getDefaultMessage();

                    //맵에 데이터 추가
                    response.put(field, message);

                });

                //response와 400에러 반환
                return ResponseEntity.status(400).body(response);

            }else {
                //받아온 객체로 카테고리 만들기
                boardCategoriesService.createCategory(boardCategoryDto);
                
                //맵형태인 response에 데이터 추가
                response.put("message", "카테고리 추가 완료");

                //200과 response반환
                return ResponseEntity.ok().body(response);
            }
            // 카테고리 업데이트 서비스 호출

        } catch (Exception e) {

            Map<String, String> response = new HashMap<>();
            response.put("message", "카테고리 추가 실패");

            return ResponseEntity.status(500).body(response);
        }
    }



}
