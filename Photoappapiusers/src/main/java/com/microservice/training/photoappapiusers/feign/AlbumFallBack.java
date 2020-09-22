package com.microservice.training.photoappapiusers.feign;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.microservice.training.photoappapiusers.data.AlbumServiceClient;
import com.microservice.training.photoappapiusers.ui.model.AlbumResponseModel;

@Component
public class AlbumFallBack implements AlbumServiceClient {

	@Override
	public List<AlbumResponseModel> getAlbums(String id) {
		// TODO Auto-generated method stub
		return new ArrayList<AlbumResponseModel>();
	}

	
}
