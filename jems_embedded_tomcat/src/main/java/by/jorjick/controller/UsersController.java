package by.jorjick.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import by.jorjick.domain.User;
import by.jorjick.service.IUserService;

/**
 * Created by gora on 1/12/17.
 */
@Controller
@RequestMapping(value = "/admin/users")
public class UsersController {
    private static final int INITIAL_PAGE_SIZE = 10; //Return one result by default
    private static final int INITIAL_PAGE = 0;
    private static final int[] PAGE_SIZES = { 5, 10, 20 };

    private IUserService userService;
  

    @Autowired
    public UsersController(IUserService userService) {
        this.userService = userService;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String usersPage() {
    	return "users";
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Page<User> users(Pageable pageable,
                        @RequestParam(value = "pageSize", required = false) Integer rpageSize,
                        @RequestParam(value = "page", required = false) Integer rpage,
                        PagedResourcesAssembler<User> assembler) {

        int pageSize = (rpageSize != null && rpageSize > 0) ? rpageSize : INITIAL_PAGE_SIZE;
        int page = (rpage != null && rpage > 0) ? rpage : INITIAL_PAGE;

        Page<User> users = userService.findAllPageable(new PageRequest(page, pageSize));
        return users;
    }
   
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<User> save(@RequestBody User user) {
    	if (userService.isExist(user)) {
    		return new ResponseEntity<User>(HttpStatus.CONFLICT);
    	}
        userService.saveAndFlush(user);
        return new ResponseEntity<User>(HttpStatus.CREATED);
    }
}
