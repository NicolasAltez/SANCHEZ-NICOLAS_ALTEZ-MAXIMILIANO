$(document).ready(() => {
  const listarTurnos = () => {
    $.ajax({
      url: "http://localhost:8080/turnos",
      type: "GET",
      dataType: "json",
      success: function (res) {
        console.log(res);
        let data = "";
        res.forEach((turno) => {
          data += `
                    <div class="col-md-4">
                    <div class="card text-dark bg-light mb-3" data-id="${
                      turno.id
                    }">
                        <div class="card-header" style="text-transform: uppercase;"><strong>Odontólogo:</strong> ${
                          turno.odontologo.nombre
                        } ${turno.odontologo.apellido}</div>
                        <div class="card-body">
                            <div class="row mb-3">
                                <div class="col">
                                    <h5 class="card-title"><strong>Turno ID:</strong> ${
                                      turno.id
                                    }</h5>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col">
                                    <p class="card-text"><strong>Matricula Odontologo:</strong> ${
                                      turno.odontologo.numeroDeMatricula
                                    }</p>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col">
                                    <p class="card-text"><strong>Fecha y Hora:</strong> ${new Date(
                                      turno.fechaYHora
                                    ).toLocaleString()}</p>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col">
                                    <p class="card-text"><strong>Paciente:</strong> ${
                                      turno.paciente.nombre
                                    } ${turno.paciente.apellido}</p>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col">
                                    <p class="card-text"><strong>DNI del paciente:</strong> ${
                                      turno.paciente.dni
                                    }</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <button id="btn-detalle-turno" class="btn btn-success mr-2">Detalle</button>
                                    <button id="btn-eliminar-turno" class="btn btn-danger mr-2">Eliminar</button>
                                    <button id="btn-actualizar-turno" class="btn btn-primary">Actualizar</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                    `;
        });

        $("#div-turnos").html(data);
      },
    });
  };

  const detalleTurno = () => {
    $(document).on("click", "#btn-detalle-turno", function () {
      let id = $(this).closest(".card").data("id");
      console.log("ID del turno:", id);
      $.ajax({
        url: `http://localhost:8080/turnos/${id}`,
        type: "GET",
        dataType: "json",
        success: function (response) {
          let detalleTurnoHtml = `
                        <p><strong>Detalle del turno con ID:</strong> ${
                          response.id
                        }</p>
                        <p><strong>Fecha y Hora:</strong> ${new Date(
                          response.fechaYHora
                        ).toLocaleString()}</p>
                        <p><strong>Odontólogo:</strong> ${
                          response.odontologo.nombre
                        } ${response.odontologo.apellido}</p>
                        <p><strong>Matricula del odontólogo:</strong> ${
                          response.odontologo.numeroDeMatricula
                        }</p>
                        <p><strong>Paciente:</strong> ${
                          response.paciente.nombre
                        } ${response.paciente.apellido}</p>
                        <p><strong>DNI del paciente:</strong> ${
                          response.paciente.dni
                        }</p>
                    `;
          $("#detalleTurno").html(detalleTurnoHtml);
          $("#modalDetalleTurno").modal("show");
        },
        error: function (xhr, status, error) {
          console.error("Error al obtener detalles del turno:", error);
        },
      });
    });
  };

  const cargarOdontologos = () => {
    $(document).on("click", "#btn-agregar-turno", function () {
      $.ajax({
        url: "http://localhost:8080/odontologos",
        type: "GET",
        dataType: "json",
        success: function (res) {
          console.log(res);
          let data = "<option value=''>Seleccionar Odontólogo</option>";
          res.forEach((odontologo) => {
            data += `
                        <option value="${odontologo.id}">${odontologo.nombre} ${odontologo.apellido}</option>
                    `;
          });
          $("#selectOdontologo").html(data);
        },
      });
    });
  };

  const cargarPacientes = () => {
    $(document).on("click", "#btn-agregar-turno", function () {
      $.ajax({
        url: "http://localhost:8080/pacientes",
        type: "GET",
        dataType: "json",
        success: function (res) {
          console.log(res);
          let data = "<option value=''>Seleccionar Paciente</option>";
          res.forEach((paciente) => {
            data += `
                        <option value="${paciente.id}">${paciente.nombre} ${paciente.apellido}</option>
                    `;
          });
          $("#selectPaciente").html(data);
        },
      });
    });
  };

  const guardarTurno = () => {
    $(document).on("click", "#guardarTurno", function () {
      let odontologoId = $("#selectOdontologo").val();
      let pacienteId = $("#selectPaciente").val();
      let fechaYHora = $("#fechaYHora").val();
      let fechaFormateada = formatearFecha(fechaYHora);
      let turno = {
        odontologoId: odontologoId,
        pacienteId: pacienteId,
        fechaYHora: fechaFormateada,
      };
      $.ajax({
        url: "http://localhost:8080/turnos",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(turno),
        success: function (response) {
          console.log("Turno guardado:", response);
          listarTurnos();
          $("#modalNuevoTurno").modal("hide");
        },
        error: function (xhr, status, error) {
          alert(xhr.responseText)
          $('#mensajes-turnos').html(xhr.responseText).css('display','block')
        },
      });
    });
  };

  const formatearFecha = (fechaYHora) => {
    const fecha = new Date(fechaYHora);
    const year = fecha.getFullYear();
    const month = String(fecha.getMonth() + 1).padStart(2, "0");
    const day = String(fecha.getDate()).padStart(2, "0");
    const hours = String(fecha.getHours()).padStart(2, "0");
    const minutes = String(fecha.getMinutes()).padStart(2, "0");
    const seconds = "00";
    const fechaFormateada = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    return fechaFormateada;
  };

  const eliminarTurno = () => {
    $(document).on("click", "#btn-eliminar-turno", function () {
      let id = $(this).closest(".card").data("id");
      console.log("ID del turno a eliminar:", id);
      $.ajax({
        url: `http://localhost:8080/turnos/${id}`,
        type: "DELETE",
        success: function (response) {
          console.log("Turno eliminado:", response);
          listarTurnos();
        },
        error: function (xhr, status, error) {
          console.error("Error al eliminar turno:", error);
        },
      });
    });
  };

  const actualizarTurno = () => {
    let id = null;
    $(document).on("click", "#btn-actualizar-turno", function () {
      id = $(this).closest(".card").data("id");
      $.ajax({
        url: `http://localhost:8080/turnos/${id}`,
        type: "GET",
        dataType: "json",
        success: function (response) {
          $("#modalActualizarTurno").modal("show");
          $("#fechaYHoraActualizacion").text(
            new Date(response.fechaYHora).toLocaleString()
          );

          $.ajax({
            url: "http://localhost:8080/odontologos",
            type: "GET",
            dataType: "json",
            success: function (res) {
              let data = "";
              res.forEach((odontologo) => {
                data += `<option value="${odontologo.id}">${odontologo.nombre} ${odontologo.apellido}</option>`;
              });
              $("#selectOdontologoActualizacion").html(data);
              $("#selectOdontologoActualizacion").val(response.odontologo.id);
            },
          });

          $.ajax({
            url: "http://localhost:8080/pacientes",
            type: "GET",
            dataType: "json",
            success: function (res) {
              let data = "";
              res.forEach((paciente) => {
                data += `<option value="${paciente.id}">${paciente.nombre} ${paciente.apellido}</option>`;
              });
              $("#selectPacienteActualizacion").html(data);
              $("#selectPacienteActualizacion").val(response.paciente.id);
            },
          });
        },
        error: function (xhr, status, error) {
          console.error("Error al obtener detalles del turno:", error);
        },
      });
    });
    $(document).on("click", "#actualizarTurno", function () {
      console.log("ID del turno a actualizar:", id);
      let pacienteId = $("#selectPacienteActualizacion").val();
      let odontologoId = $("#selectOdontologoActualizacion").val();
      let fechaYHora = $("#fechaYHoraActualizar").val();
      console.log("Fecha y hora:", fechaYHora);
      let fechaFormateada = formatearFecha(fechaYHora);
      console.log("Fecha formateada:", fechaFormateada);
      let turno = {
        odontologoId: odontologoId,
        pacienteId: pacienteId,
        fechaYHora: fechaFormateada,
      };
      console.log("Turno a actualizar:", turno);
      $.ajax({
        url: `http://localhost:8080/turnos/${id}`,
        type: "PUT",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(turno),
        success: function (response) {
          console.log("Turno actualizado:", response);
          listarTurnos();
          $("#modalActualizarTurno").modal("hide");
        },
        error: function (xhr, status, error) {
          console.error("Error al actualizar turno:", error);
        },
      });
    });
  };

  actualizarTurno();
  cargarOdontologos();
  cargarPacientes();
  listarTurnos();
  detalleTurno();
  guardarTurno();
  eliminarTurno();
});
