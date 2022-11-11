# DTO Guides

These Data Transmission Objects are defined to completely decouple the data layer from the frontend.

The backend can later on introduce new variables that can be parsed via DTOs allowing system to scale, and extend and
modify easily

It also helps masks certain inputs and prevent returning everything, and only return whats needed.

Each DTO is mapped to the entity using an instance of a `ModelMapper`.

A sample implementation is shown below.

```java
class DTOTest {
    public static void main(String[] args) {
        Product updatedProduct = productRepository.save(productToUpdate);
        return mapper.map(updatedProduct, ProductDTO.class);
    }
}
```

All relationships (associations (bidirectional/unidirectional)) are maintained at DTO level and not coupled to an
entity.