package process_statusSave;

import org.springframework.data.jpa.repository.JpaRepository;

public interface statusRepository extends JpaRepository <statusEntity, Long>{
	
	Long countByXmltojson(String status);
	Long countByJsontoxml(String status);

}
