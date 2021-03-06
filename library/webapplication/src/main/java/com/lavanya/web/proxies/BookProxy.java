package com.lavanya.web.proxies;

import  org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.lavanya.web.dto.BookDto;

/**
 * interface required to communicate with api module and make all the requests related to Book object.
 * @author lavanya
 */
@FeignClient(name = "bookApi", url = "localhost:9090")
public interface BookProxy {
	
	@GetMapping("/user/showBooks/{pageNumber}")
	Page<BookDto> getBookSearchPage(@RequestHeader(name = "Authorization") String token, @PathVariable(value = "pageNumber") int currentPage,@RequestParam(name="keyword", required=false) String keyword);

}
