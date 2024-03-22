$(document).ready(()=>{
    const listarOdontologos = () =>{
        $.ajax({
            url: "http://localhost:8080/odontologos",
            type: "GET",
            dataType: "json",
            success: function(res){
                let data = '';
                res.forEach(element =>{
                    data+=`
                    <tr odontologoId = ${element.id}>
                        <td>${element.id}</td>
                        <td>${element.numeroDeMatricula}</td>
                        <td>${element.nombre}</td>
                        <td>${element.apellido}</td>
                        <td>
                            <button id="btn-detalle-odontologo" class="btn btn-warning">Detalle</button>
                        </td>
                        <td>
                            <button id="btn-eliminar-odontologo" class="btn btn-danger">Eliminar</button>
                        </td>

                        <td>
                            <button id="btn-actualizar-odontologo" class="btn btn-primary">Actualizar</button>
                        </td>
                    </tr>
                    `
                });
    
                $('#tbody-odontologos').html(data);
            }
        });
    }
    const guardarOdontologo = () =>{
        $('#guardarOdontologo').on('click',function(){
            const odontologo = {
                numeroDeMatricula: $('#numeroDeMatricula').val(),
                nombre: $('#nombre').val(),
                apellido: $('#apellido').val()
            }

            $.ajax({
                
                url: "http://localhost:8080/odontologos",
                contentType: "application/json",
                type: "POST",
                data: JSON.stringify(odontologo),
                dataType: "json",
                success: (data) => {
                    $('#mensajes-odontologos').html('Odontologo guardado').css('display','block')
                    listarOdontologos();
                    limpiarFormulario();
                    console.log("Odontologo guardado");
                }
            });
        })
    }

    const detalleOdontologo = () => {
        $(document).on('click', '#btn-detalle-odontologo', function() {
            let btnDetalleOdontologo = $(this)[0].parentElement.parentElement;
            let id = $(btnDetalleOdontologo).attr('odontologoId');
            $.ajax({
                url: `http://localhost:8080/odontologos/${id}`,
                type: "GET",
                dataType: "json",
                success: function(response) {
                    let detalleTurnoHtml = `
                        <p><strong>Detalle del odontologo con ID:</strong> ${response.id}</p>
                        <p><strong>Numero De Matricula:</strong> ${response.numeroDeMatricula}</p>
                        <p><strong>Nombre:</strong> ${response.nombre}</p>
                        <p><strong>Apellido:</strong> ${response.apellido}</p>
                    `;
                    $("#detalleOdontologo").html(detalleTurnoHtml);
                    $("#modalDetalleOdontologo").modal("show");
                },
                error: function(xhr, status, error) {
                    console.error("Error al obtener detalles del odonotologo:", error);
                }
            });
        });
    };

    const eliminarOdontologo = () => {
        $(document).on('click','#btn-eliminar-odontologo',function(){
            if(confirm('¿Estas seguro de eliminar el odontologo?')){
                let btnEliminarOdontologo = $(this)[0].parentElement.parentElement;
                let id = $(btnEliminarOdontologo).attr('odontologoId');
                $.ajax({
                    url: `http://localhost:8080/odontologos/${id}`,
                    type: "DELETE",
                    dataType: "json",
                    success: (res) => {
                        $('#mensajes-odontologos').html('Odontologo eliminado').css('display','block')
                        listarOdontologos();
                    }
                })
            }

            
        })
    }

    const llenarDatosOdontologo = () => {
        $(document).on('click','#btn-actualizar-odontologo',function(){
            let btnEditar = $(this)[0].parentElement.parentElement;
            let id = $(btnEditar).attr('odontologoId');
            $('#guardarOdontologo').hide();
            $('#actualizarOdontologo').show();

            $.ajax({
                url: `http://localhost:8080/odontologos/${id}`,
                type: "GET",
                dataType: "json",
                success: (res) => {
                    $('#idOdontologo').val(res.id);
                    $('#numeroDeMatricula').val(res.numeroDeMatricula);
                    $('#nombre').val(res.nombre);
                    $('#apellido').val(res.apellido);
                }
            })
    })
    }

    const actualizarOdontologo = () => {
        $('#actualizarOdontologo').on('click',function(){
            let id = $('#idOdontologo').val();            
            $('#guardarOdontologo').css('display','none');
            $('#actualizarOdontologo').css('display','block');
            
           
            const odontologoAModificar = {
                numeroDeMatricula: $('#numeroDeMatricula').val(),
                nombre: $('#nombre').val(),
                apellido: $('#apellido').val()
            }
            console.log(id);

            $.ajax({
                url: `http://localhost:8080/odontologos/${id}`,
                contentType: "application/json",
                type: "PUT",
                data: JSON.stringify(odontologoAModificar),
                dataType: "json",
                success: (res) => {
                    $('#mensajes-odontologos').html('Odontologo actualizado').css('display','block');
                    $('#actualizarOdontologo').css('display','none');
                    $('#guardarOdontologo').css('display','block');
                    limpiarFormulario();
                    listarOdontologos();
                }

            })
        })

    }


    const limpiarFormulario = () =>{
        $('#numeroDeMatricula').val('');
        $('#nombre').val('');
        $('#apellido').val('');
    }

    listarOdontologos();
    guardarOdontologo();
    detalleOdontologo();
    eliminarOdontologo();
    llenarDatosOdontologo();
    actualizarOdontologo();

})
