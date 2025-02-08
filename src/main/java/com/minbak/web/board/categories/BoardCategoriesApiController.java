package com.minbak.web.board.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public List<BoardCategoryDto> getOrderedCategories() {
        List<BoardCategoryDto> boardCategories = boardCategoriesService.findOrderedCategories();
        return boardCategories;
    }

    @PostMapping("/reorder")
    public ResponseEntity<?> reOrderCategories(@RequestBody Map<String, List<Integer>> request){

        List<Integer> newOrder = request.get("order");

        // 카테고리 순서 변경 로직
        boardCategoriesService.updateCategoryOrder(newOrder);

        // 순서 변경이 완료되었음을 알리는 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "카테고리 순서가 변경되었습니다.");

        return ResponseEntity.ok(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") int id, @RequestBody BoardCategoryDto boardCategoryDto) {
        // 수정하려는 카테고리 ID와 새로운 카테고리 이름을 서비스로 전달
        try {
            // 카테고리 업데이트 서비스 호출
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
    public ResponseEntity<?> createCategory(@RequestBody BoardCategoryDto boardCategoryDto) {

        try {
            // 카테고리 업데이트 서비스 호출
            boardCategoriesService.createCategory(boardCategoryDto);

            Map<String, String> response = new HashMap<>();
            response.put("message", "카테고리 추가 완료");

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {

            Map<String, String> response = new HashMap<>();
            response.put("message", "카테고리 추가 실패");

            return ResponseEntity.status(500).body(response);
        }
    }



}
