$(document).ready(() => {

    const calendar = new VanillaCalendar('#calendar', {
        settings: {
            selection: {
                time: true,
            },
        },
    });

    calendar.init();

    const listarTurnos = () => {
        $.ajax({
            url: "http://localhost:8080/turnos",
            type: "GET",
            dataType: "json",
            success: function(res) {
                console.log(res);
                let data = "";
                res.forEach((turno) => {
                    data += `
                        <div class="col-md-4">
                            <div class="card text-dark bg-light mb-3" data-id="${turno.id}">
                                <div class="card-header">${turno.odontologo.nombre} ${turno.odontologo.apellido}</div>
                                <div class="card-body">
                                    <h5 class="card-title">Turno ID: ${turno.id}</h5>
                                    <p class="card-text">Fecha y Hora: ${new Date(turno.fechaYHora).toLocaleString()}</p>
                                    <p class="card-text">Paciente: ${turno.paciente.nombre} ${turno.paciente.apellido}</p>
                                    <p class="card-text">DNI del paciente: ${turno.paciente.dni}</p>
                                    <button id="btn-detalle-turno" class="btn btn-success">Detalle</button>
                                    <button id="btn-eliminar-turno" class="btn btn-danger">Eliminar</button>
                                    <button id="btn-actualizar-turno" class="btn btn-primary">Actualizar</button>
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
        $(document).on('click', '#btn-detalle-turno', function() {
            let id = $(this).closest(".card").data("id");
            console.log("ID del turno:", id);
            $.ajax({
                url: `http://localhost:8080/turnos/${id}`,
                type: "GET",
                dataType: "json",
                success: function(response) {
                    let detalleTurnoHtml = `
                        <p>Detalle del turno con ID: ${response.id}</p>
                        <p>Fecha y Hora: ${new Date(response.fechaYHora).toLocaleString()}</p>
                        <p>Odontólogo: ${response.odontologo.nombre} ${response.odontologo.apellido}</p>
                        <p>Paciente: ${response.paciente.nombre} ${response.paciente.apellido}</p>
                        <p>DNI del paciente: ${response.paciente.dni}</p>
                    `;
                    $("#detalleTurno").html(detalleTurnoHtml);
                    $("#modalDetalleTurno").modal("show");
                },
                error: function(xhr, status, error) {
                    console.error("Error al obtener detalles del turno:", error);
                }
            });
        });
    };

    const cargarOdontologos = () => {
        $(document).on('click','#btn-agregar-turno',function(){
        $.ajax({
            url: "http://localhost:8080/odontologos",
            type: "GET",
            dataType: "json",
            success: function(res) {
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
    })};

    const cargarPacientes = () => {
        $(document).on('click','#btn-agregar-turno',function(){
        $.ajax({
            url: "http://localhost:8080/pacientes",
            type: "GET",
            dataType: "json",
            success: function(res) {
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
    })};


    const guardarTurno = () =>{
        $(document).on('click','#guardarTurno',function(){
        let odontologoId = $("#selectOdontologo").val();
        let pacienteId = $("#selectPaciente").val();
        let fechaYHora = calendar.selectedDates;
        console.log("Fecha y Hora:", fechaYHora);
        let data = {
            odontologoId: odontologoId,
            pacienteId: pacienteId,
            fechaYHora: fechaYHora,
        };
        console.log("Datos del turno:", data);
        $.ajax({
            url: "http://localhost:8080/turnos",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function(response) {
                console.log("Turno guardado:", response);
                listarTurnos();
                $("#modalNuevoTurno").modal("hide");
            },
            error: function(xhr, status, error) {
                console.error("Error al guardar turno:", error);
            }
        });
    })};

    const eliminarTurno = () => {
        $(document).on('click', '#btn-eliminar-turno', function() {
            let id = $(this).closest(".card").data("id");
            console.log("ID del turno a eliminar:", id);
            $.ajax({
                url: `http://localhost:8080/turnos/${id}`,
                type: "DELETE",
                success: function(response) {
                    console.log("Turno eliminado:", response);
                    listarTurnos();
                },
                error: function(xhr, status, error) {
                    console.error("Error al eliminar turno:", error);
                }
            });
        });
    };

    const actualizarTurno = () => {
        $(document).on('click', '#btn-actualizar-turno', function() {
            let id = $(this).closest(".card").data("id");
            console.log("ID del turno a actualizar:", id);
            $.ajax({
                url: `http://localhost:8080/turnos/${id}`,
                type: "GET",
                dataType: "json",
                success: function(response) {

                    $("#modalActualizarTurno").modal("show");
                    $("#fechaYHoraActualizacion").text(new Date(response.fechaYHora).toLocaleString());
    
                    $.ajax({
                        url: "http://localhost:8080/odontologos",
                        type: "GET",
                        dataType: "json",
                        success: function(res) {
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
                        success: function(res) {
                            let data = "";
                            res.forEach((paciente) => {
                                data += `<option value="${paciente.id}">${paciente.nombre} ${paciente.apellido}</option>`;
                            });
                            $("#selectPacienteActualizacion").html(data);
                            $("#selectPacienteActualizacion").val(response.paciente.id);
                        },
                    });
                },
                error: function(xhr, status, error) {
                    console.error("Error al obtener detalles del turno:", error);
                }
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
