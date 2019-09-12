package visao

import java.awt.Color
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JButton

import modelo.Campo
import modelo.CampoEvento
import javax.swing.SwingUtilities

private val COR_BG_NORMAL = Color(184, 184, 184) //Cinza
private val COR_BG_MARCACAO = Color(8, 179, 247) //Azul
private val COR_BG_EXPLOSAO = Color(189, 66, 68) //Vermelho
private val COR_TXT_VERDE = Color(0, 100, 0) // Verde

class BotaoCampo(private val campo: Campo): JButton(){
    init{
        font = font.deriveFont(Font.BOLD)
        background = COR_BG_NORMAL
        isOpaque = true
        border = BorderFactory.createBevelBorder(0)
        addMouseListener(MouseCliqueListener(campo, {
            it.abrir()
        },{
            it.alterarMarcacao()
        }))

        campo.onEvento(this::aplicarEstilo)
    }

    private fun aplicarEstilo(campo: Campo, evento: CampoEvento){
        when(evento){
            CampoEvento.EXPLOSAO -> aplicarEstiloExplodido()
            CampoEvento.ABERTURA -> aplicarEstiloAberto()
            CampoEvento.MARCACAO -> aplicarEstiloMarcado()
            else -> aplicarEstiloPadrao()
        }

        // Atualiza a renderização do botão
        SwingUtilities.invokeLater{
            repaint()
            validate()
        }
    }

    private fun aplicarEstiloExplodido(){
        background = COR_BG_EXPLOSAO
        text = "X"
    }

    private fun aplicarEstiloAberto(){
        background = COR_BG_NORMAL
        border = BorderFactory.createLineBorder(Color.GRAY)

        foreground = when(campo.qtdVizinhosMinados){
            1 -> COR_TXT_VERDE
            2 -> Color.BLUE
            3 -> Color.YELLOW
            4,5,6 -> Color.RED
            else -> Color.PINK
        }
        text = if(campo.qtdVizinhosMinados > 0)
            campo.qtdVizinhosMinados.toString()
        else ""
    }

    private fun aplicarEstiloMarcado(){
        background = COR_BG_MARCACAO
        foreground = Color.BLACK
        text = "M"
    }

    private fun aplicarEstiloPadrao(){
        background = COR_BG_NORMAL
        border = BorderFactory.createBevelBorder(0)
        text = ""
    }
}