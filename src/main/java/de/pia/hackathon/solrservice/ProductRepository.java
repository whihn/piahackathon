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

	@Query(value="id:*?0* OR COLOR:*?0* OR ASSORTMENT_CATEGORY1:*?0* OR ASSORTMENT_CATEGORY2:*?0* OR ASSORTMENT_CATEGORY3:*?0*" ,filters = {"!VISIBILITY:\"ARCHIVE\" AND !VISIBILITY:\"NONE\""})
	public List<Product> findByTag(String searchTerm);

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

		public String getLongDescription() {
			return longDescription;
		}

		public String getShortDescriptionListing() {
			return shortDescriptionListing;
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

		public void setShortDescriptionListing(String shortDescriptionListing) {
			this.shortDescriptionListing = shortDescriptionListing;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getColor() {
			return color;
		}

		public String getAssortmentCategory1() {
			return assortmentCategory1;
		}

		public String getAssortmentCategory2() {
			return assortmentCategory2;
		}

		public String getAssortmentCategory3() {
			return assortmentCategory3;
		}

		@Id
		@Indexed(name = "PRODUCT_ID", type = "string")
		private String id;
		@Indexed(name = "TITLE", type = "string")
		private String name;
		@Indexed(name = "LONG_DESCRIPTION", type = "string")
		private String longDescription;
		@Indexed(name = "SHORT_DESCRIPTION_LISTING", type = "string")
		private String shortDescriptionListing;
		@Indexed(name = "PRICE", type = "string")
		private String price;
		@Indexed(name = "MAIN_IMAGE_URL", type = "string")
		private String imageUrl;
		@Indexed(name = "COLOR", type = "string")
		private String color;
		@Indexed(name = "ASSORTMENT_CATEGORY1", type = "string")
		private String assortmentCategory1;
		@Indexed(name = "ASSORTMENT_CATEGORY2", type = "string")
		private String assortmentCategory2;
		@Indexed(name = "ASSORTMENT_CATEGORY3", type = "string")
		private String assortmentCategory3;
	}
}
