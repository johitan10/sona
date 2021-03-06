package co.com.johan.green.persistencia.entity;


import javax.persistence.*;

/**
  *  
  *  @author Johan Lopez
  *  @generated	  
*/
@Entity
@Table(name="HorarioTrabajo")//, schema="${schema}")
@NamedQueries({
	@NamedQuery(name="HorarioTrabajo.obtenerTodos", query="select e from HorarioTrabajo e")
})
public class HorarioTrabajo {

	/**
	 * Identificador de la tabla HorarioTrabajo. 	
	 */
	@Id
    //@Column(name = "HorarioTrabajo_id")
    @GeneratedValue(generator = "HorarioTrabajoGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "HorarioTrabajoGen", sequenceName = "HorarioTrabajo_SEQ",allocationSize = 1)
	private Long id;

	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id=id;
	}
    
    /**
    * @generated
    * 1-1-false
    */
    
    //@Column(name = "descripcion")
    private String descripcion;
    
    /**
    * @generated
    * 1-1-false
    */
    
    //@Column(name = "porcentaje")
    private Double porcentaje;
    
    
    
    /**
    * @generated
    */
    public String getDescripcion() {
        return this.descripcion;
    }
    
    /**
    * @generated
    */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
    * @generated
    */
    public Double getPorcentaje() {
        return this.porcentaje;
    }
    
    /**
    * @generated
    */
    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
    
	
}
