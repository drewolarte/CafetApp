package com.cafetapp.app.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafetapp.app.entity.Acudiente;
import com.cafetapp.app.entity.AdministradorCafeteria;
import com.cafetapp.app.entity.AdministradorColegio;
import com.cafetapp.app.entity.Colegio;
import com.cafetapp.app.entity.Estudiante;
import com.cafetapp.app.entity.HistorialCompras;
import com.cafetapp.app.entity.SuperAdministrador;
import com.cafetapp.app.repository.AcudienteRepository;
import com.cafetapp.app.repository.AdministradorCafeteriaRepository;
import com.cafetapp.app.repository.AdministradorColegioRepository;
import com.cafetapp.app.repository.ColegioRepository;
import com.cafetapp.app.repository.EstudianteRepository;
import com.cafetapp.app.repository.HistorialComprasRepository;
import com.cafetapp.app.repository.SuperAdministradorRepository;
import com.cafetapp.app.exception.NotFoundException;

@Controller
public class HomeController {
	@Autowired
	private ColegioRepository IColegio;
	@Autowired
	private AdministradorCafeteriaRepository IAdministradorCafeteria;
	@Autowired
	private AdministradorColegioRepository IAdministradorColegio;
	@Autowired
	private AcudienteRepository IAcudiente;
	@Autowired
	private EstudianteRepository IEstudiante;
	@Autowired
	private SuperAdministradorRepository ISuperAdministrador;
	@Autowired
	private HistorialComprasRepository IHistorialCompra;

	@GetMapping("/")
	public String index(Model modelo) {
		modelo.addAttribute("colegios", IColegio.findAll());
		return "index";
	}

	@GetMapping("/{id}")
	public String home(Model model, @PathVariable("id") Long id) {
		model.addAttribute("colegio",
				IColegio.findById(id).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		return "home";
	}

	@GetMapping("/administrador/{id}")
	public String loginAdministradorColegio(Model model, @ModelAttribute AdministradorColegio administradorColegio,
			@PathVariable("id") Long id, @RequestParam(value = "alert", defaultValue = "false") Boolean alerta) {
		if (alerta) {
			model.addAttribute("alerta", "Las credenciales ingresadas son incorrectas.");
		}
		model.addAttribute("user", administradorColegio);
		model.addAttribute("colegio",
				IColegio.findById(id).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		return "loginAdministrador";
	}

	@PostMapping("/administrador/{id}")
	public String homeAdministradorColegio(Model model, @ModelAttribute AdministradorColegio administradorColegio,
			@PathVariable("id") Long id) {
		for (AdministradorColegio item : IAdministradorColegio.findAll()) {
			if (item.getId().equals(administradorColegio.getId())) {
				if (item.getContrasena().equals(administradorColegio.getContrasena())) {
					if (item.getColegio().getId() == (id)) {
						model.addAttribute("colegio", IColegio.findById(id)
								.orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
						model.addAttribute("administrador", IAdministradorColegio.findById(item.getId())
								.orElseThrow(() -> new NotFoundException("Administrador de colegio no encontrado")));
						return "homeAdministrador";
					}
				}
			}
		}

		Boolean alerta = true;
		return "redirect:/administrador/{id}?alert=" + alerta;
	}

	@GetMapping("/cafeteria/{id}")
	public String loginAdministradorCafeteria(Model model,
			@ModelAttribute AdministradorCafeteria administradorCafeteria, @PathVariable("id") Long id,
			@RequestParam(value = "alert", defaultValue = "false") Boolean alerta) {
		if (alerta) {
			model.addAttribute("alerta", "Las credenciales ingresadas son incorrectas.");
		}
		model.addAttribute("user", administradorCafeteria);
		model.addAttribute("colegio",
				IColegio.findById(id).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		return "loginCafeteria";
	}

	@PostMapping("/cafeteria/{id}")
	public String homeAdministradorCafeteria(Model model, @ModelAttribute AdministradorCafeteria administradorCafeteria,
			@PathVariable("id") Long id) {
		for (AdministradorCafeteria item : IAdministradorCafeteria.findAll()) {
			if (item.getId().equals(administradorCafeteria.getId())) {
				if (item.getContrasena().equals(administradorCafeteria.getContrasena())) {
					if (item.getColegio().getId() == (id)) {
						model.addAttribute("colegio", IColegio.findById(id)
								.orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
						model.addAttribute("cafeteria", IAdministradorCafeteria.findById(item.getId())
								.orElseThrow(() -> new NotFoundException("Administrador de cafeteria no encontrado")));
						return "homeCafeteria";
					}
				}
			}
		}
		Boolean alerta = true;
		return "redirect:/cafeteria/{id}?alert=" + alerta;
	}

	@GetMapping("/acudiente/{id}")
	public String loginAcudiente(Model model, @ModelAttribute Acudiente acudiente, @PathVariable("id") Long id,
			@RequestParam(value = "alert", defaultValue = "false") Boolean alerta) {
		if (alerta) {
			model.addAttribute("alerta", "Las credenciales ingresadas son incorrectas.");
		}
		model.addAttribute("user", acudiente);
		model.addAttribute("colegio",
				IColegio.findById(id).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		return "loginAcudiente";
	}

	@PostMapping("/acudiente/{id}")
	public String homeAcudiente(Model model, @ModelAttribute Acudiente acudiente, @PathVariable("id") Long id) {
		for (Acudiente item : IAcudiente.findAll()) {
			if (item.getCedula().equals(acudiente.getCedula())) {
				if (item.getContrasena().equals(acudiente.getContrasena())) {
					if (item.getColegio().getId() == (id)) {
						model.addAttribute("colegio", IColegio.findById(id)
								.orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
						model.addAttribute("acudiente", item);
						ArrayList<Estudiante> estudiantes = new ArrayList<>();
						for (Estudiante estudiante : IEstudiante.findAll()) {
							if (estudiante.getAcudiente().getCedula().equals(item.getCedula())
									&& estudiante.getColegio().getId() == id) {
								estudiantes.add(estudiante);
							}
						}
						model.addAttribute("estudiantes", estudiantes);
						return "homeAcudiente";
					}
				}
			}
		}
		Boolean alerta = true;
		return "redirect:/acudiente/{id}?alert=" + alerta;
	}

	@GetMapping("/administrador/{id}/{adminId}")
	public String homeAdministrador(Model model, @PathVariable("id") Long id, @PathVariable("adminId") String adminId) {
		model.addAttribute("colegio",
				IColegio.findById(id).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("administrador", IAdministradorColegio.findById(adminId)
				.orElseThrow(() -> new NotFoundException("Administrador de colegio no encontrado")));
		return "homeAdministrador";
	}

	@GetMapping("/administrador/acudientes/{id}/{adminId}")
	public String gestionarAcudientes(Model model, @PathVariable("id") Long id, @PathVariable("adminId") String adminId,
			@RequestParam(value = "alert", defaultValue = "false") Boolean alerta,
			@RequestParam(value = "error", defaultValue = "false") Boolean error) {
		if (alerta) {
			model.addAttribute("alerta", "El acudiente se eliminó satisfactoriamente.");
		} else if (error) {
			model.addAttribute("error", "El acudiente no se puede eliminar porque tiene estudiantes asignados.");
		}
		ArrayList<Acudiente> acudientes = new ArrayList<>();
		for (Acudiente item : IAcudiente.findAll()) {
			if (item.getColegio().getId() == id) {
				acudientes.add(item);
			}
		}
		model.addAttribute("colegio",
				IColegio.findById(id).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("acudientes", acudientes);
		model.addAttribute("administrador", IAdministradorColegio.findById(adminId)
				.orElseThrow(() -> new NotFoundException("Administrador de colegio no encontrado")));
		return "gestionAcudientes";
	}

	@GetMapping("/administrador/acudientes/agregar/{idCol}/{adminId}")
	public String agregarAcudientes(Model model, @PathVariable("idCol") Long idCol,
			@PathVariable("adminId") String adminId, @ModelAttribute Acudiente acudiente,
			@RequestParam(value = "alert", defaultValue = "false") Boolean alerta) {
		if (alerta) {
			model.addAttribute("alerta", "El acudiente ya se encuentra registrado.");
		}
		model.addAttribute("colegio",
				IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("administrador", IAdministradorColegio.findById(adminId)
				.orElseThrow(() -> new NotFoundException("Administrador de colegio no encontrado")));
		model.addAttribute("user", acudiente);
		return "agregarAcudiente";
	}

	@PostMapping("/administrador/acudientes/agregar/{idCol}/{adminId}")
	public String agregarAcudiente(@ModelAttribute("user") Acudiente acudiente, @PathVariable("idCol") Long idCol,
			@PathVariable("adminId") String adminId) {
		Colegio colegio = IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado"));
		for (Acudiente item : IAcudiente.findAll()) {
			if (item.getColegio().getId() == idCol && item.getCedula().equals(acudiente.getCedula())) {
				Boolean alerta = true;
				return "redirect:/administrador/acudientes/agregar/{idCol}/{adminId}?alert=" + alerta;
			}
		}
		acudiente.setColegio(colegio);
		IAcudiente.save(acudiente);
		return "redirect:/administrador/acudientes/{idCol}/{adminId}";
	}

	@GetMapping("/administrador/acudientes/eliminar/{idCol}/{adminId}/{acuId}")
	public String eliminarAcudiente(@PathVariable("idCol") Long idCol, @PathVariable("adminId") String adminId,
			@PathVariable("acuId") Long acuId) {
		try {
			IAcudiente.deleteById(acuId);
			Boolean alerta = true;
			return "redirect:/administrador/acudientes/{idCol}/{adminId}?alert=" + alerta;

		} catch (Exception e) {
			Boolean error = true;
			return "redirect:/administrador/acudientes/{idCol}/{adminId}?error=" + error;
		}

	}

	@GetMapping("/administrador/acudientes/editar/{idCol}/{adminId}/{acuId}")
	public String editarAcudientes(Model model, @PathVariable("idCol") Long idCol,
			@PathVariable("adminId") String adminId, @PathVariable("acuId") Long acuId) {
		model.addAttribute("colegio",
				IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("administrador", IAdministradorColegio.findById(adminId)
				.orElseThrow(() -> new NotFoundException("Administrador de colegio no encontrado")));
		model.addAttribute("user", IAcudiente.findById(acuId)
				.orElseThrow(() -> new NotFoundException("Acudiente de colegio no encontrado")));
		return "editarAcudiente";
	}

	@PostMapping("/administrador/acudientes/editar/{idCol}/{adminId}")
	public String editarAcudiente(@ModelAttribute("user") Acudiente acudiente, @PathVariable("idCol") Long idCol,
			@PathVariable("adminId") String adminId) {
		Colegio colegio = IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado"));
		acudiente.setColegio(colegio);
		IAcudiente.save(acudiente);
		return "redirect:/administrador/acudientes/{idCol}/{adminId}";
	}

	@GetMapping("/administrador/cafeterias/{id}/{adminId}")
	public String gestionarCafeterias(Model model, @PathVariable("id") Long id, @PathVariable("adminId") String adminId,
			@RequestParam(value = "alert", defaultValue = "false") Boolean alerta) {
		if (alerta) {
			model.addAttribute("alerta", "El administrador de cafetería se eliminó satisfactoriamente.");
		}
		ArrayList<AdministradorCafeteria> cafeterias = new ArrayList<>();
		for (AdministradorCafeteria item : IAdministradorCafeteria.findAll()) {
			if (item.getColegio().getId() == id) {
				cafeterias.add(item);
			}
		}
		model.addAttribute("colegio",
				IColegio.findById(id).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("cafeterias", cafeterias);
		model.addAttribute("administrador", IAdministradorColegio.findById(adminId)
				.orElseThrow(() -> new NotFoundException("Administrador de colegio no encontrado")));
		return "gestionCafeterias";
	}

	@GetMapping("/administrador/cafeterias/agregar/{idCol}/{adminId}")
	public String agregarCafeterias(Model model, @PathVariable("idCol") Long idCol,
			@PathVariable("adminId") String adminId, @ModelAttribute AdministradorCafeteria cafeteria,
			@RequestParam(value = "alert", defaultValue = "false") Boolean alerta) {
		if (alerta) {
			model.addAttribute("alerta", "El administrador de cafetería ya se encuentra registrado.");
		}
		model.addAttribute("colegio",
				IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("administrador", IAdministradorColegio.findById(adminId)
				.orElseThrow(() -> new NotFoundException("Administrador de colegio no encontrado")));
		model.addAttribute("user", cafeteria);
		return "agregarCafeteria";
	}

	@PostMapping("/administrador/cafeterias/agregar/{idCol}/{adminId}")
	public String agregarCafeterias(@ModelAttribute("user") AdministradorCafeteria cafeteria,
			@PathVariable("idCol") Long idCol, @PathVariable("adminId") String adminId) {
		Colegio colegio = IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado"));
		for (AdministradorCafeteria item : IAdministradorCafeteria.findAll()) {
			if (item.getColegio().getId() == idCol && item.getId().equals(cafeteria.getId())) {
				Boolean alerta = true;
				return "redirect:/administrador/cafeterias/agregar/{idCol}/{adminId}?alert=" + alerta;
			}
		}
		cafeteria.setColegio(colegio);
		IAdministradorCafeteria.save(cafeteria);
		return "redirect:/administrador/cafeterias/{idCol}/{adminId}";
	}

	@GetMapping("/administrador/cafeterias/eliminar/{idCol}/{adminId}/{cafeId}")
	public String eliminarCafeteria(@PathVariable("idCol") Long idCol, @PathVariable("adminId") String adminId,
			@PathVariable("cafeId") String cafeId) {
		IAdministradorCafeteria.deleteById(cafeId);
		Boolean alerta = true;
		return "redirect:/administrador/cafeterias/{idCol}/{adminId}?alert=" + alerta;
	}

	@GetMapping("/administrador/cafeterias/editar/{idCol}/{adminId}/{cafeId}")
	public String editarCafeterias(Model model, @PathVariable("idCol") Long idCol,
			@PathVariable("adminId") String adminId, @PathVariable("cafeId") String cafeId) {
		model.addAttribute("colegio",
				IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("administrador", IAdministradorColegio.findById(adminId)
				.orElseThrow(() -> new NotFoundException("Administrador de colegio no encontrado")));
		model.addAttribute("user", IAdministradorCafeteria.findById(cafeId)
				.orElseThrow(() -> new NotFoundException("Administrador de Cafeteria no encontrado")));
		return "editarCafeteria";
	}

	@PostMapping("/administrador/cafeterias/editar/{idCol}/{adminId}")
	public String editarCafeteria(@ModelAttribute("user") AdministradorCafeteria cafeteria,
			@PathVariable("idCol") Long idCol, @PathVariable("adminId") String adminId) {
		Colegio colegio = IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado"));
		cafeteria.setColegio(colegio);
		IAdministradorCafeteria.save(cafeteria);
		return "redirect:/administrador/cafeterias/{idCol}/{adminId}";
	}

	@GetMapping("/administrador/estudiantes/{id}/{adminId}")
	public String gestionarEstudiantes(Model model, @PathVariable("id") Long id,
			@PathVariable("adminId") String adminId,
			@RequestParam(value = "alert", defaultValue = "false") Boolean alerta,
			@RequestParam(value = "acu", defaultValue = "false") Boolean acu,
			@RequestParam(value = "error", defaultValue = "false") Boolean error) {
		if (alerta) {
			model.addAttribute("alerta", "El estudiante se eliminó satisfactoriamente.");
		} else if (acu) {
			model.addAttribute("acu", "El acudiente no se encuentra registrado.");
		} else if (error) {
			model.addAttribute("acu", "El estudiante no se puede eliminar porque tiene compras registradas.");
		}
		ArrayList<Estudiante> estudiantes = new ArrayList<>();
		for (Estudiante item : IEstudiante.findAll()) {
			if (item.getColegio().getId() == id) {
				estudiantes.add(item);
			}
		}
		model.addAttribute("colegio",
				IColegio.findById(id).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("estudiantes", estudiantes);
		model.addAttribute("administrador", IAdministradorColegio.findById(adminId)
				.orElseThrow(() -> new NotFoundException("Administrador de colegio no encontrado")));
		return "gestionEstudiantes";
	}

	@GetMapping("/administrador/estudiantes/agregar/{idCol}/{adminId}")
	public String agregarEstudiantes(Model model, @PathVariable("idCol") Long idCol,
			@PathVariable("adminId") String adminId, @ModelAttribute Estudiante estudiante,
			@RequestParam(value = "alert", defaultValue = "false") Boolean alerta,
			@RequestParam(value = "acu", defaultValue = "false") Boolean acu) {
		if (alerta) {
			model.addAttribute("alerta", "El estudiante ya se encuentra registrado.");
		} else if (acu) {
			model.addAttribute("alerta", "El acudiente no se encuentra registrado.");
		}
		model.addAttribute("colegio",
				IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("administrador", IAdministradorColegio.findById(adminId)
				.orElseThrow(() -> new NotFoundException("Administrador de colegio no encontrado")));
		model.addAttribute("user", estudiante);
		return "agregarEstudiante";
	}

	@PostMapping("/administrador/estudiantes/agregar/{idCol}/{adminId}")
	public String agregarEstudiantes(@ModelAttribute("user") Estudiante estudiante, @PathVariable("idCol") Long idCol,
			@PathVariable("adminId") String adminId) {
		Colegio colegio = IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado"));
		for (Estudiante item : IEstudiante.findAll()) {
			if (item.getColegio().getId() == idCol && item.getId().equals(estudiante.getId())) {
				Boolean alerta = true;
				return "redirect:/administrador/estudiantes/agregar/{idCol}/{adminId}?alert=" + alerta;
			}
		}
		for (Acudiente item : IAcudiente.findAll()) {
			if (item.getColegio().getId() == idCol && item.getCedula().equals(estudiante.getAcudiente().getCedula())) {
				estudiante.setColegio(colegio);
				Acudiente acudiente = IAcudiente.findById(item.getId())
						.orElseThrow(() -> new NotFoundException("Acudiente no encontrado"));
				estudiante.setAcudiente(acudiente);
				estudiante.setRestricciones("Ninguna.");
				IEstudiante.save(estudiante);
				return "redirect:/administrador/estudiantes/{idCol}/{adminId}";
			}
		}
		Boolean acu = true;
		return "redirect:/administrador/estudiantes/agregar/{idCol}/{adminId}?acu=" + acu;

	}

	@GetMapping("/administrador/estudiantes/eliminar/{idCol}/{adminId}/{stuId}")
	public String eliminarEstudiante(@PathVariable("idCol") Long idCol, @PathVariable("adminId") String adminId,
			@PathVariable("stuId") String stuId) {
		try {
			IEstudiante.deleteById(stuId);
			Boolean alerta = true;
			return "redirect:/administrador/estudiantes/{idCol}/{adminId}?alert=" + alerta;
		} catch (Exception e) {
			Boolean error = true;
			return "redirect:/administrador/estudiantes/{idCol}/{adminId}?error=" + error;
		}

	}

	@GetMapping("/administrador/estudiantes/editar/{idCol}/{adminId}/{stuId}")
	public String editarEstudiantes(Model model, @PathVariable("idCol") Long idCol,
			@PathVariable("adminId") String adminId, @PathVariable("stuId") String stuId) {
		model.addAttribute("colegio",
				IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("administrador", IAdministradorColegio.findById(adminId)
				.orElseThrow(() -> new NotFoundException("Administrador de colegio no encontrado")));
		model.addAttribute("user",
				IEstudiante.findById(stuId).orElseThrow(() -> new NotFoundException("Estudiante no encontrado")));
		return "editarEstudiante";
	}

	@PostMapping("/administrador/estudiantes/editar/{idCol}/{adminId}")
	public String editarEstudiante(@ModelAttribute("user") Estudiante estudiante, @PathVariable("idCol") Long idCol,
			@PathVariable("adminId") String adminId) {
		Colegio colegio = IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado"));
		for (Acudiente item : IAcudiente.findAll()) {
			if (item.getColegio().getId() == idCol && item.getCedula().equals(estudiante.getAcudiente().getCedula())) {
				estudiante.setColegio(colegio);
				Acudiente acudiente = IAcudiente.findById(item.getId())
						.orElseThrow(() -> new NotFoundException("Acudiente no encontrado"));
				estudiante.setAcudiente(acudiente);
				IEstudiante.save(estudiante);
				return "redirect:/administrador/estudiantes/{idCol}/{adminId}";
			}
		}
		Boolean acu = true;
		return "redirect:/administrador/estudiantes/{idCol}/{adminId}?acu=" + acu;
	}
	// ↑ Roles Normales
	// ↓ SuperAdministrador

	@GetMapping("/superadministrador")
	public String loginSuperAdministrador(Model model, @ModelAttribute SuperAdministrador superAdministrador,
			@RequestParam(value = "alert", defaultValue = "false") Boolean alerta) {
		if (alerta) {
			model.addAttribute("alerta", "Las credenciales ingresadas son incorrectas.");
		}
		model.addAttribute("user", superAdministrador);
		return "loginSuperAdministrador";
	}

	@PostMapping("/superadministrador")
	public String homeSuperAdministrador(Model model, @ModelAttribute SuperAdministrador superAdministrador) {
		for (SuperAdministrador item : ISuperAdministrador.findAll()) {
			if (item.getId().equals(superAdministrador.getId())) {
				if (item.getContrasena().equals(superAdministrador.getContrasena())) {
					model.addAttribute("superadministrador", ISuperAdministrador.findById(item.getId())
							.orElseThrow(() -> new NotFoundException("SuperAdministrador no encontrado")));
					return "homeSuperAdministrador";
				}
			}
		}
		Boolean alerta = true;
		return "redirect:/superadministrador?alert=" + alerta;
	}

	@GetMapping("/superadministrador/colegios/{adminId}")
	public String gestionarColegios(Model model, @PathVariable("adminId") String adminId,
			@RequestParam(value = "alert", defaultValue = "false") Boolean alerta,
			@RequestParam(value = "error", defaultValue = "false") Boolean error,
			@RequestParam(value = "recarga", defaultValue = "false") Boolean recarga,
			@RequestParam(value = "compra", defaultValue = "false") Boolean compra) {
		if (alerta) {
			model.addAttribute("alerta", "El colegio se eliminó satisfactoriamente.");
		} else if (error) {
			model.addAttribute("error", "El colegio no se puede eliminar porque tiene usuarios registrados.");
		} else if (recarga) {
			model.addAttribute("error",
					"El valor de la recarga mínima no puede superar el valor de la recarga máxima.");
		} else if (compra) {
			model.addAttribute("error", "El valor de la compra mínima no puede superar el valor de la compra máxima.");
		}
		model.addAttribute("colegios", IColegio.findAll());
		model.addAttribute("superadministrador", ISuperAdministrador.findById(adminId)
				.orElseThrow(() -> new NotFoundException("SuperAdministrador no encontrado")));
		return "gestionColegios";
	}

	@GetMapping("/superadministrador/{adminId}")
	public String homeSuperAdministrador(Model model, @PathVariable("adminId") String adminId) {
		model.addAttribute("superadministrador", ISuperAdministrador.findById(adminId)
				.orElseThrow(() -> new NotFoundException("SuperAdministrador no encontrado")));
		return "homeSuperAdministrador";
	}

	@GetMapping("/superadministrador/colegios/agregar/{adminId}")
	public String agregarColegios(Model model, @PathVariable("adminId") String adminId, @ModelAttribute Colegio colegio,
			@RequestParam(value = "alert", defaultValue = "false") Boolean alerta,
			@RequestParam(value = "recarga", defaultValue = "false") Boolean recarga,
			@RequestParam(value = "compra", defaultValue = "false") Boolean compra) {
		if (alerta) {
			model.addAttribute("alerta", "El colegio ya se encuentra registrado.");
		} else if (recarga) {
			model.addAttribute("alerta",
					"El valor de la recarga mínima no puede superar el valor de la recarga máxima.");
		} else if (compra) {
			model.addAttribute("alerta", "El valor de la compra mínima no puede superar el valor de la compra máxima.");
		}
		model.addAttribute("superadministrador", ISuperAdministrador.findById(adminId)
				.orElseThrow(() -> new NotFoundException("SuperAdministrador no encontrado")));
		model.addAttribute("user", colegio);
		return "agregarColegio";
	}

	@PostMapping("/superadministrador/colegios/agregar/{adminId}")
	public String agregarColegios(@ModelAttribute("user") Colegio colegio, @PathVariable("adminId") String adminId) {
		for (Colegio item : IColegio.findAll()) {
			if (item.getNombre().equalsIgnoreCase(colegio.getNombre())) {
				Boolean alerta = true;
				return "redirect:/superadministrador/colegios/agregar/{adminId}?alert=" + alerta;
			}
		}
		if (colegio.getMaxRecarga() <= colegio.getMinRecarga()) {
			Boolean recarga = true;
			return "redirect:/superadministrador/colegios/agregar/{adminId}?recarga=" + recarga;
		}
		if (colegio.getMaxCompra() <= colegio.getMinCompra()) {
			Boolean compra = true;
			return "redirect:/superadministrador/colegios/agregar/{adminId}?compra=" + compra;
		}
		IColegio.save(colegio);
		return "redirect:/superadministrador/colegios/{adminId}";

	}

	@GetMapping("/superadministrador/colegios/eliminar/{adminId}/{colId}")
	public String eliminarColegio(@PathVariable("adminId") String adminId, @PathVariable("colId") Long colId) {
		try {
			IColegio.deleteById(colId);
			Boolean alerta = true;
			return "redirect:/superadministrador/colegios/{adminId}?alert=" + alerta;
		} catch (Exception e) {
			Boolean error = true;
			return "redirect:/superadministrador/colegios/{adminId}?error=" + error;
		}

	}

	@GetMapping("/superadministrador/colegios/editar/{adminId}/{colId}")
	public String editarColegios(Model model, @PathVariable("adminId") String adminId,
			@PathVariable("colId") Long colId) {
		model.addAttribute("superadministrador", ISuperAdministrador.findById(adminId)
				.orElseThrow(() -> new NotFoundException("SuperAdministrador no encontrado")));
		model.addAttribute("user",
				IColegio.findById(colId).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		return "editarColegio";
	}

	@PostMapping("/superadministrador/colegios/editar/{adminId}")
	public String editarColegios(@ModelAttribute("user") Colegio colegio, @PathVariable("adminId") String adminId) {
		if (colegio.getMaxRecarga() <= colegio.getMinRecarga()) {
			Boolean recarga = true;
			return "redirect:/superadministrador/colegios/{adminId}?recarga=" + recarga;
		}
		if (colegio.getMaxCompra() <= colegio.getMinCompra()) {
			Boolean compra = true;
			return "redirect:/superadministrador/colegios/{adminId}?compra=" + compra;
		}
		for (Colegio item : IColegio.findAll()) {
			if (item.getNombre().equalsIgnoreCase(colegio.getNombre())) {
				Colegio col = IColegio.findById(colegio.getId())
						.orElseThrow(() -> new NotFoundException("Colegio no encontrado"));
				colegio.setNombre(col.getNombre());
				IColegio.save(colegio);
				return "redirect:/superadministrador/colegios/{adminId}";
			}
		}
		IColegio.save(colegio);
		return "redirect:/superadministrador/colegios/{adminId}";
	}

	@GetMapping("/superadministrador/administradores/{adminId}")
	public String gestionarAdministradores(Model model, @PathVariable("adminId") String adminId,
			@RequestParam(value = "alert", defaultValue = "false") Boolean alerta) {
		if (alerta) {
			model.addAttribute("alerta", "El administrador de colegio se eliminó satisfactoriamente.");
		}
		ArrayList<AdministradorColegio> administradores = new ArrayList<>();
		for (AdministradorColegio item : IAdministradorColegio.findAll()) {
			administradores.add(item);
		}
		administradores
				.sort((elem1, elem2) -> elem1.getColegio().getNombre().compareTo(elem2.getColegio().getNombre()));
		model.addAttribute("administradores", administradores);
		model.addAttribute("superadministrador", ISuperAdministrador.findById(adminId)
				.orElseThrow(() -> new NotFoundException("SuperAdministrador no encontrado")));
		return "gestionAdministradores";
	}

	@GetMapping("/superadministrador/administradores/agregar/{adminId}")
	public String agregarAdministradores(Model model, @PathVariable("adminId") String adminId,
			@ModelAttribute AdministradorColegio administrador,
			@RequestParam(value = "alert", defaultValue = "false") Boolean alerta) {
		if (alerta) {
			model.addAttribute("alerta", "El administrador de colegio ya se encuentra registrado.");
		}
		model.addAttribute("superadministrador", ISuperAdministrador.findById(adminId)
				.orElseThrow(() -> new NotFoundException("SuperAdministrador no encontrado")));
		model.addAttribute("user", administrador);

		model.addAttribute("colegios", IColegio.findAll());
		return "agregarAdministrador";
	}

	@PostMapping("/superadministrador/administradores/agregar/{adminId}")
	public String agregarAdministradores(@ModelAttribute("user") AdministradorColegio administrador,
			@PathVariable("adminId") String adminId) {
		for (AdministradorColegio item : IAdministradorColegio.findAll()) {
			if (item.getId().equals(administrador.getId())) {
				Boolean alerta = true;
				return "redirect:/superadministrador/administradores/agregar/{adminId}?alert=" + alerta;
			}
		}
		IAdministradorColegio.save(administrador);
		return "redirect:/superadministrador/administradores/{adminId}";

	}

	@GetMapping("/superadministrador/administradores/eliminar/{adminId}/{adminColId}")
	public String eliminarAdministrador(@PathVariable("adminId") String adminId,
			@PathVariable("adminColId") String adminColId) {
		IAdministradorColegio.deleteById(adminColId);
		Boolean alerta = true;
		return "redirect:/superadministrador/administradores/{adminId}?alert=" + alerta;
	}

	@GetMapping("/superadministrador/administradores/editar/{adminId}/{adminColId}")
	public String editarAdministradores(Model model, @PathVariable("adminId") String adminId,
			@PathVariable("adminColId") String adminColId) {
		model.addAttribute("superadministrador", ISuperAdministrador.findById(adminId)
				.orElseThrow(() -> new NotFoundException("SuperAdministrador no encontrado")));
		model.addAttribute("user", IAdministradorColegio.findById(adminColId)
				.orElseThrow(() -> new NotFoundException("Administrador de colegio no encontrado")));
		model.addAttribute("colegios", IColegio.findAll());
		return "editarAdministrador";
	}

	@PostMapping("/superadministrador/administradores/editar/{adminId}")
	public String editarAdministradores(@ModelAttribute("user") AdministradorColegio administrador,
			@PathVariable("adminId") String adminId) {
		IAdministradorColegio.save(administrador);
		return "redirect:/superadministrador/administradores/{adminId}";
	}

	@GetMapping("/acudiente/{id}/{acuId}")
	public String homeAcudiente(Model model, @PathVariable("id") Long id, @PathVariable("acuId") Long acuId) {
		Acudiente acudiente = IAcudiente.findById(acuId)
				.orElseThrow(() -> new NotFoundException("Acudiente no encontrado"));
		model.addAttribute("colegio",
				IColegio.findById(id).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("acudiente", acudiente);
		ArrayList<Estudiante> estudiantes = new ArrayList<>();
		for (Estudiante estudiante : IEstudiante.findAll()) {
			if (estudiante.getAcudiente().getCedula().equals(acudiente.getCedula())
					&& estudiante.getColegio().getId() == id) {
				estudiantes.add(estudiante);
			}
		}
		model.addAttribute("estudiantes", estudiantes);
		return "homeAcudiente";
	}

	@GetMapping("/acudiente/saldo/{idCol}/{acuId}/{stuId}")
	public String editarSaldos(Model model, @PathVariable("idCol") Long idCol, @PathVariable("acuId") Long acuId,
			@PathVariable("stuId") String stuId) {
		model.addAttribute("colegio",
				IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("acudiente", IAcudiente.findById(acuId)
				.orElseThrow(() -> new NotFoundException("Acudiente de colegio no encontrado")));
		Estudiante student = IEstudiante.findById(stuId)
				.orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
		student.setSaldo(0);
		model.addAttribute("user", student);
		return "editarSaldo";

	}

	@PostMapping("/acudiente/saldo/{idCol}/{acuId}")
	public String editarSaldos(@ModelAttribute("user") Estudiante estudiante, @PathVariable("idCol") Long idCol,
			@PathVariable("acuId") Long acuId) {
		Estudiante student = IEstudiante.findById(estudiante.getId())
				.orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
		estudiante.setSaldo(estudiante.getSaldo() + student.getSaldo());
		IEstudiante.save(estudiante);
		return "redirect:/acudiente/{idCol}/{acuId}";
	}

	@GetMapping("/acudiente/topeDiario/{idCol}/{acuId}/{stuId}")
	public String editarTopeDiarios(Model model, @PathVariable("idCol") Long idCol, @PathVariable("acuId") Long acuId,
			@PathVariable("stuId") String stuId) {
		model.addAttribute("colegio",
				IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("acudiente", IAcudiente.findById(acuId)
				.orElseThrow(() -> new NotFoundException("Acudiente de colegio no encontrado")));
		Estudiante student = IEstudiante.findById(stuId)
				.orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
		model.addAttribute("user", student);
		return "editarTopeDiario";

	}

	@PostMapping("/acudiente/topeDiario/{idCol}/{acuId}")
	public String editarTopeDiarios(@ModelAttribute("user") Estudiante estudiante, @PathVariable("idCol") Long idCol,
			@PathVariable("acuId") Long acuId) {
		IEstudiante.save(estudiante);
		return "redirect:/acudiente/{idCol}/{acuId}";
	}

	@GetMapping("/acudiente/restricciones/{idCol}/{acuId}/{stuId}")
	public String editarRestricciones(Model model, @PathVariable("idCol") Long idCol, @PathVariable("acuId") Long acuId,
			@PathVariable("stuId") String stuId) {
		model.addAttribute("colegio",
				IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("acudiente", IAcudiente.findById(acuId)
				.orElseThrow(() -> new NotFoundException("Acudiente de colegio no encontrado")));
		Estudiante student = IEstudiante.findById(stuId)
				.orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
		model.addAttribute("user", student);
		return "editarRestricciones";
	}

	@PostMapping("/acudiente/restricciones/{idCol}/{acuId}")
	public String editarRestricciones(@ModelAttribute("user") Estudiante estudiante, @PathVariable("idCol") Long idCol,
			@PathVariable("acuId") Long acuId) {
		if (estudiante.getRestricciones().length() <= 0) {
			estudiante.setRestricciones("Ninguna.");
		}
		IEstudiante.save(estudiante);
		return "redirect:/acudiente/{idCol}/{acuId}";
	}

	@GetMapping("/acudiente/historial/{idCol}/{acuId}/{studId}")
	public String historial(Model model, @PathVariable("idCol") Long idCol, @PathVariable("acuId") Long acuId,
			@PathVariable("studId") String studId) {
		Acudiente acudiente = IAcudiente.findById(acuId)
				.orElseThrow(() -> new NotFoundException("Acudiente no encontrado"));
		model.addAttribute("colegio",
				IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("acudiente", acudiente);
		ArrayList<HistorialCompras> historiales = new ArrayList<>();
		for (HistorialCompras historial : IHistorialCompra.findAll()) {
			if (historial.getEstudiante().getId().equals(studId) && historial.getColegio().getId() == idCol) {
				historiales.add(historial);
			}
		}
		historiales.sort(Comparator.comparing(HistorialCompras::getFechaCompra, Comparator.reverseOrder()));
		model.addAttribute("historiales", historiales);
		model.addAttribute("estudiante",
				IEstudiante.findById(studId).orElseThrow(() -> new NotFoundException("Estudiante no encontrado")));
		return "historialCompras";
	}

	@PostMapping("/cafeteria/buscar/{idCol}/{cafeId}")
	public String buscar(Model model, @RequestParam("id") String id, @PathVariable("idCol") Long idCol,
			@PathVariable("cafeId") String cafeId) {
		model.addAttribute("colegio",
				IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("cafeteria", IAdministradorCafeteria.findById(cafeId)
				.orElseThrow(() -> new NotFoundException("Administrador de cafetería no encontrado")));
		try {
			Estudiante estudiante = IEstudiante.findById(id)
					.orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
			if (estudiante.getColegio().getId() == idCol) {
				model.addAttribute("estudiante", estudiante);
				return "gestionCompra";
			} else {
				Boolean alerta = true;
				return "redirect:/cafeteria/{idCol}/{cafeId}?alert=" + alerta;
			}

		} catch (Exception e) {
			Boolean alerta = true;
			return "redirect:/cafeteria/{idCol}/{cafeId}?alert=" + alerta;
		}
	}

	@GetMapping("/cafeteria/{idCol}/{cafeId}")
	public String homeCafeteria(Model model, @PathVariable("idCol") Long idCol, @PathVariable("cafeId") String cafeId,
			@RequestParam(value = "alert", defaultValue = "false") Boolean alerta) {
		if (alerta) {
			model.addAttribute("alerta", "El estudiante no se encuentra registrado.");
		}
		AdministradorCafeteria cafeteria = IAdministradorCafeteria.findById(cafeId)
				.orElseThrow(() -> new NotFoundException("Administrador de cafeteria no encontrado"));
		model.addAttribute("colegio",
				IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("cafeteria", cafeteria);
		return "homeCafeteria";
	}

	@PostMapping("/cafeteria/compra/{idCol}/{cafeId}")
	public String comprar(Model model, @RequestParam("productos") String productos, @RequestParam("pago") double pago,
			@ModelAttribute("estudiante") Estudiante estudiante, @PathVariable("idCol") Long idCol,
			@PathVariable("cafeId") String cafeId) {
		model.addAttribute("colegio",
				IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("cafeteria", IAdministradorCafeteria.findById(cafeId)
				.orElseThrow(() -> new NotFoundException("Administrador de cafetería no encontrado")));
		Estudiante student = IEstudiante.findById(estudiante.getId())
				.orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
		model.addAttribute("estudiante", student);
		Calendar fecha = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<HistorialCompras> compras = new ArrayList<>();
		double sumaCompras = 0;
		for (HistorialCompras item : IHistorialCompra.findAll()) {
			if (item.getColegio().getId() == idCol && item.getEstudiante().getId().equals(student.getId())
					&& item.getFechaCompra().equals(formatter.format(fecha.getTime()))) {
				compras.add(item);
				sumaCompras += item.getValorPago();
			}
		}
		if (student.getTopeDiario() == 0 || (pago + sumaCompras) <= student.getTopeDiario()) {
			if (student.getSaldo() >= pago) {
				student.setSaldo(student.getSaldo() - pago);
				IEstudiante.save(student);
				HistorialCompras compra = new HistorialCompras();
				compra.setProductos(productos);
				compra.setValorPago(pago);
				compra.setColegio(student.getColegio());
				compra.setEstudiante(student);
				compra.setFechaCompra(formatter.format(fecha.getTime()));
				IHistorialCompra.save(compra);
				return "redirect:/cafeteria/compra/{idCol}/{cafeId}?stuId=" + student.getId();
			} else {
				Boolean alerta = true;
				return "redirect:/cafeteria/compra/{idCol}/{cafeId}?stuId=" + student.getId() + "&alert=" + alerta;
			}
		} else if (sumaCompras >= student.getTopeDiario()) {
			Boolean tope = true;
			return "redirect:/cafeteria/compra/{idCol}/{cafeId}?stuId=" + student.getId() + "&tope=" + tope;
		} else if ((pago + sumaCompras) > student.getTopeDiario()) {
			double disp = student.getTopeDiario() - sumaCompras;
			return "redirect:/cafeteria/compra/{idCol}/{cafeId}?stuId=" + student.getId() + "&disp=" + disp;
		}
		return "redirect:/cafeteria/compra/{idCol}/{cafeId}?stuId=" + student.getId();

	}

	@GetMapping("/cafeteria/compra/{idCol}/{cafeId}")
	public String gestionCompra(Model model, @PathVariable("idCol") Long idCol, @PathVariable("cafeId") String cafeId,
			@RequestParam("stuId") String stuId, @RequestParam(value = "alert", defaultValue = "false") Boolean alerta,
			@RequestParam(value = "tope", defaultValue = "false") Boolean tope,
			@RequestParam(value = "disp", defaultValue = "0") double disp) {
		if (alerta) {
			model.addAttribute("alerta", "El saldo es insuficiente para realizar la compra.");
		} else if (tope) {
			model.addAttribute("alerta", "El estudiante alcanzó el tope diario para realizar compras.");
		} else if (disp != 0) {
			model.addAttribute("alerta",
					"El saldo disponible para realizar compras diarias sin superar el tope es de $" + disp + " COP.");
		}
		model.addAttribute("colegio",
				IColegio.findById(idCol).orElseThrow(() -> new NotFoundException("Colegio no encontrado")));
		model.addAttribute("cafeteria", IAdministradorCafeteria.findById(cafeId)
				.orElseThrow(() -> new NotFoundException("Administrador de cafetería no encontrado")));
		Estudiante student = IEstudiante.findById(stuId)
				.orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
		model.addAttribute("estudiante", student);
		return "gestionCompra";
	}

}
