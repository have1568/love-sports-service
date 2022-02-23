package love.sports.manage.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangXinzhu
 * @date 2022/2/20 17:21
 * @since 1.0
 */
@RestController
@RequestMapping
public class IndexController {

    @RequestMapping
    public ResponseEntity<Object> index(){
        return ResponseEntity.ok("OK");
    }
}
