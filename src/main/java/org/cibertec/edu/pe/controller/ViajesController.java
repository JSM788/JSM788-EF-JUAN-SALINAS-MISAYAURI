package org.cibertec.edu.pe.controller;

import org.cibertec.edu.pe.model.Ticket;
import org.cibertec.edu.pe.model.Ciudad;
import org.cibertec.edu.pe.model.DetalleVenta;
import org.cibertec.edu.pe.model.Venta;
import org.cibertec.edu.pe.repository.ICiudadRepository;
import org.cibertec.edu.pe.repository.IVentaDetalleRepository;
import org.cibertec.edu.pe.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@SessionAttributes({"ticketsAgregados"})
public class ViajesController {

    @Autowired
    private ICiudadRepository ciudadRepository;

    @Autowired
    private IVentaRepository ventaRepository;

    @Autowired
    private IVentaDetalleRepository ventaDetalleRepository;

    @GetMapping("/")
    public String inicioSlash(Model model) {
        List<Ciudad> ciudades = ciudadRepository.findAll();
        List<Ticket> tickets = (List<Ticket>) model.getAttribute("ticketsAgregados");

        if (tickets.size() > 0) {
            Ticket ticketEncontrado = tickets.get(tickets.size() - 1);
            model.addAttribute("ticket", ticketEncontrado);
        } else {
            model.addAttribute("ticket", new Ticket());
        }

        model.addAttribute("ciudades", ciudades);
        return "index";
    }

    @PostMapping("/agregar-ticket")
    public String agregarTicket(Model model, @ModelAttribute Ticket ticket) {
        List<Ciudad> ciudades = ciudadRepository.findAll();
        List<Ticket> tickets = (List<Ticket>) model.getAttribute("ticketsAgregados");
        Double precioTicket = 50.00;

        if (todosLosCamposLlenos(ticket)) {
            ticket.setSubTotal(ticket.getCantidad() * precioTicket);
            tickets.add(ticket);
        } else {
            model.addAttribute("mensajeError", "Por favor, complete todos los campos antes de agregar el ticket.");
        }

        model.addAttribute("ticketsAgregados", tickets);
        model.addAttribute("ciudades", ciudades);
        model.addAttribute("ticket", new Ticket());

        return "redirect:/";
    }

    private boolean todosLosCamposLlenos(Ticket ticket) {
        return ticket.getCiudadOrigen() != null &&
               ticket.getCiudadDestino() != null &&
               ticket.getFechaSalida() != null &&
               ticket.getFechaRetorno() != null &&
               ticket.getNombreComprador() != null &&
               ticket.getCantidad() > 0;
    }

    @ModelAttribute("ticketsAgregados")
    public List<Ticket> ticketsComprados() {
        return new ArrayList<>();
    }

    @GetMapping("/procesar-compra")
    public String procesarCompra(Model model) {
        List<Ticket> tickets = (List<Ticket>) model.getAttribute("ticketsAgregados");
        
        if (tickets.isEmpty()) {
            return "redirect:/";
        }
        
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            double montoTotal = 0.0;
            for (Ticket ticket : tickets) {
                montoTotal += ticket.getSubTotal();
            }

            Venta nuevaVenta = new Venta();
            nuevaVenta.setFechaVenta(new Date());
            nuevaVenta.setMontoTotal(montoTotal);
            nuevaVenta.setNombreComprador(tickets.get(0).getNombreComprador());

            Venta ventaGuardada = ventaRepository.save(nuevaVenta);
            for (Ticket ticket : tickets) {
                DetalleVenta ventaDetalle = new DetalleVenta();

                Ciudad ciudadDestino = ciudadRepository.findById(ticket.getCiudadDestino()).get();
                ventaDetalle.setCiudadDestino(ciudadDestino);
                Ciudad ciudadOrigen = ciudadRepository.findById(ticket.getCiudadOrigen()).get();
                ventaDetalle.setCiudadOrigen(ciudadOrigen);

                ventaDetalle.setCantidad(ticket.getCantidad());
                ventaDetalle.setSubTotal(ticket.getSubTotal());

                Date fechaRetorno = formatter.parse(ticket.getFechaRetorno());
                ventaDetalle.setFechaRetorno(fechaRetorno);

                Date fechaSalida = formatter.parse(ticket.getFechaSalida());
                ventaDetalle.setFechaViaje(fechaSalida);
                ventaDetalle.setVenta(ventaGuardada);
                ventaDetalleRepository.save(ventaDetalle);
            }

            model.addAttribute("ticketsAgregados", new ArrayList<>());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "confirmar";
    }

    @GetMapping("/limpiarModelo")
    public String limpiarModelo(Model model) {
        List<Ciudad> ciudades = ciudadRepository.findAll();

        model.addAttribute("ticket", new Ticket());
        model.addAttribute("ciudades", ciudades);
        model.addAttribute("ticketsAgregados", new ArrayList<>());

        return "index";
    }
}
