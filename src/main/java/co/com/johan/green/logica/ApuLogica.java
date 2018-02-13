package co.com.johan.green.logica;

import co.com.johan.green.dto.*;
import co.com.johan.green.persistencia.*;
import co.com.johan.green.persistencia.entity.*;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @generated @author Johan Lopez
 */
@Stateless
public class ApuLogica {

    @EJB
    private ApuDAO persistencia;

    @EJB
    private ApuItemLogica apuItemLogica;

    @EJB
    private ApuItemDAO apuItemDAO;

    @EJB
    private SalariosRecargosLogica salariosRecargosLogica;

    private final DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Retorna una lista con los Apu que se encuentran en la base de datos
     *
     * @return retorna una lista de ApuDTO
     * @generated
     */
    public List<ApuDTO> obtenerTodos() {
        List<ApuDTO> apus = convertirEntidad(persistencia.obtenerTodos());
        if (!apus.isEmpty()) {
            for (ApuDTO apu : apus) {
                apu.setItems(apuItemLogica.obtenerPorApu(apu.getId()));
                apu.setValorTotal(this.valorTotalApu(apu.getItems()));
            }
        }
        return apus;
    }

    private Double valorTotalApu(List<ApuItemDTO> items) {
        Double valorTotal = 0D;
        if (items != null && !items.isEmpty()) {
            for (ApuItemDTO item : items) {
                if (item.getMaterial() != null) {
                    valorTotal += item.getMaterial().getPrecio() * item.getCantidad();
                }
                if (item.getHerramienta() != null) {
                    valorTotal += ((item.getHerramienta().getValor() * item.getHerramienta().getPorcentaje()) / 100) * item.getCantidad();
                }
                if (item.getCargo() != null) {
                    Double valorTotalCargo = 0D;
                    List<SalariosRecargosDTO> salarios = salariosRecargosLogica.obtenerPorCargo(item.getCargo().getId());
                    if (!salarios.isEmpty()) {
                        for (SalariosRecargosDTO salario : salarios) {
                            if (salario.getActivo()) {
                                valorTotalCargo += (salario.getValor() * salario.getCantidad()) / 100;
                            }
                        }
                    }
                    valorTotal += ((valorTotalCargo / 30) / 8 /60) * item.getCantidad();
                }
            }
        }
        return valorTotal;
    }

    /**
     * Obtiene Apu dado su identificador.
     *
     * @param id identificador del elemento Apu
     * @return Apu del id dado
     * @generated
     */
    public ApuDTO obtener(Long id) {
        ApuDTO apu = convertirEntidad(persistencia.obtener(id));
        apu.setItems(apuItemLogica.obtenerPorApu(apu.getId()));
        return apu;
    }

    /**
     * Almacena la informacion de Apu
     *
     * @param dto Apu a guardar
     * @return Apu con los cambios realizados por el proceso de guardar
     * @generated
     */
    public ApuDTO guardar(ApuDTO dto) {
        ApuDTO apu = convertirEntidad(persistencia.guardar(convertirDTO(dto)));
        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            for (ApuItemDTO apuItem : dto.getItems()) {
                apuItem.setApu(apu);
                apuItemLogica.guardar(apuItem);
            }
        }
        return apu;
    }

    /**
     * Elimina el registro Apu con el identificador dado
     *
     * @param id identificador del Apu
     * @generated
     */
    public void borrar(Long id) {
        persistencia.borrar(id);
    }

    /**
     * Actualiza la informacion de Apu
     *
     * @param dto Apu a guardar
     * @generated
     */
    public void actualizar(ApuDTO dto) {
        persistencia.actualizar(convertirDTO(dto));
        apuItemDAO.borrarPorApu(dto.getId());
        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            for (ApuItemDTO apuItem : dto.getItems()) {
                apuItem.setApu(dto);
                apuItemLogica.guardar(apuItem);
            }
        }
    }

    /**
     * Convierte un ApuDTO en la entidad Apu.
     *
     * @param dto a convertir
     * @return entidad Apu
     * @generated
     */
    public Apu convertirDTO(ApuDTO dto) {
        if (dto == null) {
            return null;
        }
        Apu entidad = new Apu();
        entidad.setId(dto.getId());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setActivo(dto.getActivo());
        if (dto.getFechaCreacion() != null) {
            entidad.setFechaCreacion(LocalDateTime.parse(dto.getFechaCreacion(), formatoFechaHora));
        }
        if (dto.getFechaModificacion() != null) {
            entidad.setFechaModificacion(LocalDateTime.parse(dto.getFechaModificacion(), formatoFechaHora));
        }
        if (dto.getEstadoApu() != null) {
            entidad.setEstadoApu(new EstadoApu(dto.getEstadoApu().getId()));
        }
        if (dto.getUnidad()!= null) {
            entidad.setUnidad(new Unidad(dto.getUnidad().getId()));
        }
        return entidad;
    }

    /**
     * Convierte una lista de ApuDTO en una lista de Apu.
     *
     * @param dtos a convertir
     * @return entidades List<Apu>
     * @generated
     */
    public List<Apu> convertirDTO(List<ApuDTO> dtos) {
        List<Apu> entidades = new ArrayList<Apu>();
        for (ApuDTO dto : dtos) {
            entidades.add(convertirDTO(dto));
        }
        return entidades;
    }

    /**
     * Convierte Apu en un DTO ApuDTO.
     *
     * @param entidad a convertir
     * @return dto ApuDTO
     * @generated
     */
    public ApuDTO convertirEntidad(Apu entidad) {
        ApuDTO dto = new ApuDTO();
        dto.setId(entidad.getId());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setActivo(entidad.getActivo());
        if (entidad.getFechaCreacion() != null) {
            dto.setFechaCreacion(formatoFechaHora.format(entidad.getFechaCreacion()));
        }
        if (entidad.getFechaModificacion() != null) {
            dto.setFechaModificacion(formatoFechaHora.format(entidad.getFechaModificacion()));
        }
        if (entidad.getEstadoApu() != null) {
            dto.setEstadoApu(new EstadoApuDTO(entidad.getEstadoApu().getId()));
            dto.getEstadoApu().setDescripcion(entidad.getEstadoApu().getDescripcion());
        }
        if (entidad.getUnidad()!= null) {
            dto.setUnidad(new UnidadDTO(entidad.getUnidad().getId()));
            dto.getUnidad().setDescripcion(entidad.getUnidad().getDescripcion());
        }
        return dto;
    }

    /**
     * Convierte una lista de Apu en una lista de ApuDTO.
     *
     * @param entidades a convertir
     * @return dtos List<ApuDTO>
     * @generated
     */
    public List<ApuDTO> convertirEntidad(List<Apu> entidades) {
        List<ApuDTO> dtos = new ArrayList<ApuDTO>();
        for (Apu entidad : entidades) {
            dtos.add(convertirEntidad(entidad));
        }
        return dtos;
    }

}
