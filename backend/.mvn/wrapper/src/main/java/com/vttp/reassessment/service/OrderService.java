package com.vttp.reassessment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.vttp.reassessment.model.Order;
import com.vttp.reassessment.model.Response;
import com.vttp.reassessment.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
        
    
	public void cachePosting(Order order) {
    
            redisTemplate.opsForList().leftPush(posting_id.getId());
            redisTemplate.opsForHash().put("_Map", sdf.getId());
        }
	}

    public Order getPostingFromCache(String id) {
        List<Order> orders = orderRepo.getPostingFromCache(id);
		List<Response> orderSummary = new ArrayList<>();
		for(int i = 0; i < orders.size(); i++) {
			Order o = orders.get(i);
			Response os = new Response();
			os.setPosting_Id(o.getPosting_Id());
			os.setName(o.getName());
			os.setEmail(o.getEmail());
			Response.add(os);
		}
		return Response;
    }

    public boolean insertPosting(String posting_id, String string, String name, String email, String phone,
            String title, String description, String image) {
        return false;
    }
