package de.pia.hackathon.solrservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.pia.hackathon.solrservice.ProductRepository.Product;

@RestController
@RequestMapping("/search")
public class SolrRestService {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/name")
	public List<ProductRepository.Product> findByName(@RequestParam("q") String name) {
		return productRepository.findByName(name);
	}
	
	@GetMapping("/all")
	public List<ProductRepository.Product> findByCustomQuery(@RequestParam("q") String query) {
		return productRepository.findByCustomQuery(query);
	}
	
	@GetMapping("/id")
	public Optional<Product> findById(@RequestParam("id") String query) {
		return productRepository.findById(query);
	}
}
