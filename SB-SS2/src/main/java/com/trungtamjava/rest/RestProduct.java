package com.trungtamjava.rest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.service.ProductService;

@RestController
public class RestProduct {
	@Autowired
	ProductService productService;
	
	@PostMapping(value = "/product/add")
	public ProductDTO add(@ModelAttribute(name ="product" ) ProductDTO productDTO
			) {
		
		String originalFilename = productDTO.getFile().getOriginalFilename();
		int lastIndex = originalFilename.lastIndexOf(".");
		String ext = originalFilename.substring(lastIndex);

		String avatarFilename = System.currentTimeMillis() + ext;
		File newfile = new File("D:\\test\\" + avatarFilename);
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(newfile);
			fileOutputStream.write(productDTO.getFile().getBytes());
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		productDTO.setImage(avatarFilename);
		
		productService.insert(productDTO);
		return productDTO;
	}
	
	@GetMapping(value = "/product/search")
	public List<ProductDTO> listProduct(int start,int lenght){
		List<ProductDTO> list = productService.getAll(start, lenght);
		return list;
	}
	
	@GetMapping(value = "/product/get/{id}")
	public ProductDTO getProduct(@PathVariable(name = "id") int id) {
		return productService.get(id);
	}
	

	@PostMapping(value = "/product/update")
	public ProductDTO update(@ModelAttribute(name = "product") ProductDTO productDTO
			) {
		
		String originalFilename = productDTO.getFile().getOriginalFilename();
		int lastIndex = originalFilename.lastIndexOf(".");
		String ext = originalFilename.substring(lastIndex);

		String avatarFilename = System.currentTimeMillis() + ext;
		File newfile = new File("D:\\test\\" + avatarFilename);
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(newfile);
			fileOutputStream.write(productDTO.getFile().getBytes());
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		productDTO.setImage(avatarFilename);
		productService.update(productDTO);
		return productDTO;
	}
	
	@GetMapping(value = "/product/delete/{id}")
	public void delete(@PathVariable(name = "id") int id) {
		productService.delete(id);
	}
}
