window.addEventListener('load', function () {


    const formulario = document.querySelector('#update_turno_form');

        formulario.addEventListener('submit', function (event) {
        let turnoId = document.querySelector('#turno_id').value;


        const formData = {
                             id: document.querySelector('#turno_id').value,
                             fecha: document.querySelector('#fecha').value,
                             pacienteId: document.querySelector('#paciente_id').value,
                             odontologoId: document.querySelector('#odontologo_id').value
        };


        const url = '/turnos';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
          fetch(url,settings)
          .then(response => response.json())

    })
 })

function findBy(id) {
          const url = '/turnos'+ '/buscar'+ "/" +id;
          const settings = {
              method: 'GET'
          }
          fetch(url,settings)
          .then(response => response.json())
          .then(data => {
              let turno = data;
              document.querySelector('#turno_id').value= turno.id;
              document.querySelector('#fecha').value= turno.fecha;
               document.querySelector('#paciente_id').value= turno.pacienteId;
              document.querySelector('#odontologo_id').value= turno.odontologoId;
              document.querySelector('#div_turno_updating').style.display = "block";
          }).catch(error => {
              alert("Error: " + error);
          })
      }