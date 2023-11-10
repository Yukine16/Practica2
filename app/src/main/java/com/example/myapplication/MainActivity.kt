package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Preguntas
import java.util.Random

class MainActivity : ComponentActivity() {
    private val listaPreguntas = listOf(
        Preguntas(
            "¿Esto es un elefante?",
            true,
            "¡Correcto! mas concretamente un doble elefante telepata de guerra",
            "¡Mal! Vete a un oculista",
            R.drawable.elefante
        ),
        Preguntas(
            "¿Esto es un coche?",
            false,
            "¡Correcto! Depende de como le de el aire es una batidora o un cohete ruso",
            "Que no te engañe, depende de como le de el aire es una batidora o un cohete ruso",
            R.drawable.batidora_jpg
        ),
        Preguntas(
            "¿El color que se muestra en la foto es rojo?",
            false,
            "¡Correcto!",
            "¡Mal! Vete a un oculista porque posiblemente seas daltonico",
            R.drawable.azul
        ),
        Preguntas(
            "¿El tenerrife es el mejor?",
            true,
            "¡Correcto!",
            "¡Mal! Sea cual sea tu equipo, el tenerife es mejor",
            R.drawable.tete
        ),
        Preguntas(
            "¿11 + 22 = 33?",
            true,
            "¡Correcto!",
            "¡Mal! igual que ferrari desde hace 20 años",
            R.drawable._3
        )
    )

    var indicePreguntaActual: Int = 0
    var respuestaSeleccionada: Boolean = false
    var verdaderoHabilitado: Boolean = true
    var falsoHabilitado: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            app()
        }
    }

    @Composable
    public fun app(){

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val preguntaActual = listaPreguntas[indicePreguntaActual]
            val respuestaTexto = remember { mutableStateOf("") }
            val respuestaColor = remember { mutableStateOf(Color.Black) }

            Text(
                text = preguntaActual.texto,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )

            Image(
                painter = painterResource(id = preguntaActual.imagen),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
            )


            Text(
                text = respuestaTexto.value,
                color = respuestaColor.value,
                fontSize = 16.sp,
                modifier = Modifier.padding(16.dp)
            )

            if (!respuestaSeleccionada) {
                Row(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Button(
                        onClick = {
                            verificarRespuesta(true, preguntaActual, respuestaTexto, respuestaColor)

                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Green,
                            contentColor = Color.Black
                        ),
                        enabled = verdaderoHabilitado
                    ) {
                        Text(text = "Verdadero")
                    }

                    Button(
                        onClick = {
                            verificarRespuesta(
                                false,
                                preguntaActual,
                                respuestaTexto,
                                respuestaColor
                            )
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.Black
                        ),
                        enabled = falsoHabilitado
                    ) {
                        Text(text = "Falso")
                    }
                }
            }


                Row(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Button(
                        onClick = {
                            retorcederPregunta()
                            respuestaTexto.value = ""
                        },
                        modifier = Modifier.weight(1f)
                    )

                    {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = " ")
                        Text(text = "Anterior")
                    }

                    Button(
                        onClick = {
                            avanzarSiguientePregunta()
                            respuestaTexto.value = ""
                        },
                        modifier = Modifier.weight(1f),

                        ) {
                        Text(text = "Siguiente")
                        Icon(Icons.Outlined.ArrowForward, contentDescription = "")
                    }
                }

                Button(
                    onClick = {
                        preguntaAleatoria()
                        respuestaTexto.value = ""
                    }
                ) {
                    Text(text = "Pregunta Aleatoria")
                }
            }

    }


    private fun verificarRespuesta(
        respuestaUsuario: Boolean,
        preguntaActual: Preguntas,
        respuestaTexto: MutableState<String>,
        respuestaColor: MutableState<Color>
    ) {
        val mensaje: String
        val colorTexto: Color

        if (respuestaUsuario == preguntaActual.valor) {
            mensaje = preguntaActual.res_correcta
            colorTexto = Color.Green
        } else {
            mensaje = preguntaActual.res_incorrecta
            colorTexto = Color.Red
        }

        respuestaTexto.value = mensaje
        respuestaColor.value = colorTexto

        respuestaSeleccionada = true

        if (respuestaUsuario) {
            falsoHabilitado = false
        } else {
            verdaderoHabilitado = false
        }

    }

    private fun avanzarSiguientePregunta() {
        if (respuestaSeleccionada) {
            indicePreguntaActual++
            if (indicePreguntaActual >= listaPreguntas.size) {
                indicePreguntaActual = 0
            }
            respuestaSeleccionada = false
            verdaderoHabilitado = true
            falsoHabilitado = true
        }
    }

    private fun retorcederPregunta() {
        if (respuestaSeleccionada) {
            indicePreguntaActual--
            if (indicePreguntaActual < 0) {
                indicePreguntaActual = listaPreguntas.size - 1
            }
            respuestaSeleccionada = false
            verdaderoHabilitado = true
            falsoHabilitado = true
        }
    }


   private fun preguntaAleatoria(){
       if(respuestaSeleccionada) {
           indicePreguntaActual = (0 until listaPreguntas.size).random()
           respuestaSeleccionada = false
           verdaderoHabilitado = true
           falsoHabilitado = true
       }
    }


}





