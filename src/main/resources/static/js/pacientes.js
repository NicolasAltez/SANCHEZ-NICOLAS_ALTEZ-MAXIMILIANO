$(document).ready(()=>{
    const listarOdontologos = () =>{
        $.ajax({
            url: "http://localhost:8080/pacientes",
            type: "GET",
            dataType: "json",
            success: function(res){
                console.log(res);
                let data = '';
                res.forEach(element =>{
                    data+=`
                    <tr pacienteId = ${element.id}>
                        <td>${element.id}</td>
                        <td>${element.nombre}</td>
                        <td>${element.apellido}</td>
                        <td>${element.dni}</td>
                        <td>${element.fechaIngreso}</td>
                        <td>${element.calle}</td>
                        <td>${element.numero}</td>
                        <td>${element.localidad}</td>
                        <td>${element.provincia}</td>
                        <td>
                            <button id="btn-detalle-pacietne" class="btn btn-warning">Detalle</button>
                        </td>
                        <td>
                            <button id="btn-eliminar-paciente" class="btn btn-danger">Eliminar</button>
                        </td>

                        <td>
                            <button id="btn-actualizar-odontologo" class="btn btn-primary">Actualizar</button>
                        </td>
                    </tr>
                    `
                });

                $('#tbody-pacientes').html(data);
            }
        });
    }
    const guardarPaciente = () =>{
        $('#guardarPaciente').on('click',function(){
            const domicilio = {
                calle: $('#calle').val(),
                numero: $('#numero').val(),
                localidad: $('#localidad').val(),
                provincia: $('#provincia').val(),
            }
            const paciente = {
                nombre: $('#nombre').val(),
                apellido: $('#apellido').val(),
                dni: $('#dni').val(),
                fechaIngreso: $('#fechaIngreso').val(),
                domicilio: domicilio
            }

            $.ajax({

                url: "http://localhost:8080/odontologos",
                contentType: "application/json",
                type: "POST",
                data: JSON.stringify(paciente),
                dataType: "json",
                success: (data) => {
                    $('#mensajes-pacientes').html('Paciente guardado').css('display','block')
                    listarPacientes();
                    limpiarFormulario();
                    console.log("Paciente guardado");
                }
            });
        })
    }

    const detallePaciente = () =>{
        $(document).on('click','#btn-detalle-paciente',function(){
            let btnDetallePaciente = $(this)[0].parentElement.parentElement;
            let id = $(btnDetallePaciente).attr('pacienteId');
            $.ajax({
                url: `http://localhost:8080/paciente/${id}`,
                type: "GET",
                dataType: "json",
                success: (res) => {
                    let data = `
                    <strong>Nombre:</strong> ${res.nombre} - <strong>Apellido:</strong> ${res.apellido} - <strong>Dni:</strong> ${res.dni} - <strong>FechaIngreso:</strong> ${res.fechaIngreso} - <strong>Calle:</strong> ${res.calle} - <strong>Numero:</strong> ${res.numero} - <strong>Localidad:</strong> ${res.localidad} - <strong>Provincia:</strong> ${res.provincia} <br><br> // ver----------------------
                    <button id="btn-limpiar" class="btn btn-warning">Limpiar</button>
                    `
                    let paciente = $('#paciente-detalle').html(data);
                    $('#btn-limpiar').on('click', () =>{
                        paciente.html('');
                    })
                }
            })
        })
    }

    const eliminarPaciente = () => {
        $(document).on('click','#btn-eliminar-paciente',function(){
            if(confirm('¿Estas seguro de eliminar el paciente?')){
                let btnEliminarPaciente = $(this)[0].parentElement.parentElement;
                let id = $(btnEliminarPaciente).attr('pacienteId');
                $.ajax({
                    url: `http://localhost:8080/pacientes/${id}`,
                    type: "DELETE",
                    dataType: "json",
                    success: (res) => {
                        $('#mensajes-pacientes').html('Paciente eliminado').css('display','block')
                        listarPacientes();
                    }
                })
            }


        })
    }

    const llenarDatosPaciente = () => {
        $(document).on('click','#btn-actualizar-paciente',function(){
            let btnEditar = $(this)[0].parentElement.parentElement;
            let id = $(btnEditar).attr('pacienteId');
            $('#guardarPaciente').hide();
            $('#actualizarPaciente').show();

            $.ajax({
                url: `http://localhost:8080/pacientes/${id}`,
                type: "GET",
                dataType: "json",
                success: (res) => {
                    $('#nombre').val(res.nombre);
                    $('#apellido').val(res.apellido);
                    $('#dni').val(res.dni);
                    $('#fechaIngreso').val(res.fechaIngreso);
                    $('#domicilio').val(res.calle, res.numero, res.localidad, res.provincia);  //ver---------------------


                }
            })
    })
    }

    const actualizarPaciente = () => {
        $('#actualizarPaciente').on('click',function(){
            let id = $('#idPaciente').val();

            $('#guardarPaciente').css('display','none');
            $('#actualizarPaciente').css('display','block');

            const domicilioAModificar = {
                calle: $('#calle').val()
                numero: $('#numero').val()
                localidad: $('#localidad').val()
                provincia: $('#provincia').val()

            }

            const pacienteAModificar = {
                nombre: $('#nombre').val(),
                apellido: $('#apellido').val()
                dni: $('#dni').val()
                fechaIngreso: $('#apellido').val()
                domicilio: domicilioAModificar


            }
            console.log(pacienteAModificar)
            console.log(id);

            $.ajax({
                url: `http://localhost:8080/odontologos/actualizar/${id}`,
                contentType: "application/json",
                type: "PUT",
                data: JSON.stringify(pacienteAModificar),
                dataType: "json",
                success: (res) => {
                    $('#mensajes-pacientes').html('Paciente actualizado').css('display','block');
                    $('#actualizarPaciente').css('display','none');
                    $('#guardarPaciente').css('display','block');
                    limpiarFormulario();
                    listarPacientes();
                }

            })
        })

    }


    const limpiarFormulario = () =>{
        $('#nombre').val('');
        $('#apellido').val('');
        $('#dni').val('');
        $('#fechaIngreso').val('');
        $('#calle').val('');
        $('#numero').val('');
        $('#localidad').val('');
        $('#provincia').val('');
    }

    listarPacientes();
    guardarPaciente();
    detallePaciente();
    eliminarPaciente();
    llenarDatosPaciente();
    actualizarPaciente();

})
