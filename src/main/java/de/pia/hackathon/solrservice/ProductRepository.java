package de.pia.hackathon.solrservice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import de.pia.hackathon.solrservice.ProductRepository.Product;

@Repository
public interface ProductRepository extends SolrCrudRepository<Product, String> {
	@Query(value="TITLE:*?0*",filters = {"!VISIBILITY:\"ARCHIVE\" AND !VISIBILITY:\"NONE\""})
	public List<Product> findByName(String name);

	public Optional<Product> findById(String pid);
	
	@Query(value="id:*?0* OR TITLE:*?0*" ,filters = {"!VISIBILITY:\"ARCHIVE\" AND !VISIBILITY:\"NONE\""})
	public List<Product> findByCustomQuery(String searchTerm);

	@SolrDocument(collection = "tchibo")
	public class Product {
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getImageUrl() {
			return imageUrl;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
		@Id
		@Indexed(name = "PRODUCT_ID", type = "string")
		private String id;
		@Indexed(name = "TITLE", type = "string")
		private String name;
		@Indexed(name = "PRICE", type = "string")
		private String price;
		@Indexed(name = "MAIN_IMAGE_URL", type = "string")
		private String imageUrl;
	}
}
