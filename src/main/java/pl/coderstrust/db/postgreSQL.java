package pl.coderstrust.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.coderstrust.model.Invoice;

@Repository
public interface postgreSQL extends CrudRepository<Invoice, Long> {
}
