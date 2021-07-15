package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.CommentDao;
import com.trungtamjava.entity.Comment;
import com.trungtamjava.entity.Product;
import com.trungtamjava.entity.User;
import com.trungtamjava.model.CommentDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.service.CommentService;
import com.trungtamjava.ultil.DateTimeUtils;



@Transactional
@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentDao commentDao;

	@Override
	public void add(CommentDTO commentDTO) {
		Comment comment = new Comment();
		comment.setContent(commentDTO.getContent());
		Product product= new Product();
		product.setId(commentDTO.getProductDTO().getId());
		comment.setProduct(product);
		
		User user = new User();
		user.setId(commentDTO.getUserDTO().getId());
//		user.setName(commentDTO.getUserDTO().getName());
		comment.setUser(user);
		comment.setCreatedDate(new Date());
		commentDao.add(comment);
	}

	@Override
	public void update(CommentDTO commentDTO) {
		Comment comment = commentDao.get(commentDTO.getId());
		if (comment != null) {
			comment.setContent(commentDTO.getContent());
			commentDao.update(comment);
		}

	}

	@Override
	public void delete(int id) {
		Comment comment = commentDao.get(id);
		if (comment != null) {
			commentDao.delete(id);
		}
	}

	@Override
	public CommentDTO get(int id) {
		Comment comment = commentDao.get(id);
		return convert(comment);
	}

	@Override
	public List<CommentDTO> searchByProduct(int id) {
		List<Comment> listComments= commentDao.searchByProduct(id);
		List<CommentDTO> commentDTOs= new ArrayList<>();
		for(Comment comment : listComments) {
			CommentDTO commentDTO= new CommentDTO();
			commentDTO.setContent(comment.getContent());
			commentDTO.setCreatedDate(String.valueOf(comment.getCreatedDate()));
			
			ProductDTO productDTO= new ProductDTO();
			productDTO.setId(comment.getProduct().getId());
			commentDTO.setProductDTO(productDTO);
			
			UserDTO userDTO = new UserDTO();
			userDTO.setName(comment.getUser().getName());
			commentDTO.setUserDTO(userDTO);
			
			commentDTOs.add(commentDTO);
		}
		return commentDTOs;
	}
	
	public CommentDTO convert(Comment comment) {
		CommentDTO commentDTO=new CommentDTO();
		commentDTO.setId(comment.getId());
		commentDTO.setContent(comment.getContent());
		commentDTO.setCreatedDate(DateTimeUtils.formatDate(comment.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
		
		ProductDTO productDTO=new ProductDTO();
		productDTO.setId(comment.getProduct().getId());
		commentDTO.setProductDTO(productDTO);
		
		UserDTO userDTO=new UserDTO();
		userDTO.setId(comment.getUser().getId());
		commentDTO.setUserDTO(userDTO);
		return commentDTO;
	}
}

