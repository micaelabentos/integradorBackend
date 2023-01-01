 window.addEventListener('load', function () {

     const formulario = document.querySelector('#add_new_turno');
     formulario.addEventListener('submit', function (event) {

          const formData = {
                   fecha: document.querySelector('#fecha').value,
                   pacienteId: document.querySelector('#paciente_id').value,
                   odontologoId: document.querySelector('#odontologo_id').value

               };


         const url = '/turnos';
         const settings = {
             method: 'POST',
             headers: {
                 'Content-Type': 'application/json',
             },
             body: JSON.stringify(formData)
         }

         fetch(url, settings)
             .then(response => response.json())
             .then(data => {
                console.log(data)
                  let successAlert = '<div class="alert alert-success alert-dismissible">' +
                      '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                      '<strong>Turno agregado</strong> </div>'

                  document.querySelector('#response').innerHTML = successAlert;
                  document.querySelector('#response').style.display = "block";
                  resetUploadForm();

             })
             .catch(error => {
                console.log(error)
                     let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                      '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                                      '<strong> Error intente nuevamente</strong> </div>'

                       document.querySelector('#response').innerHTML = errorAlert;
                       document.querySelector('#response').style.display = "block";
                      resetUploadForm();})
     });


     function resetUploadForm(){
           document.querySelector('#fecha').value = "";
           document.querySelector('#paciente_id').value = "";
           document.querySelector('#odontologo_id').value = "";
       }


 });