package com.microservice.training.photoappapiusers.data;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.training.photoappapiusers.feign.AlbumFallBack;
import com.microservice.training.photoappapiusers.ui.model.AlbumResponseModel;

@FeignClient(name = "albumapi",fallback = AlbumFallBack.class)
public interface AlbumServiceClient {

	
	@GetMapping(path = "/users/{id}/albums")
	public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}
