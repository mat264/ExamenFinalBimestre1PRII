package ljAntDron;

public class ljAntCiberDron implements ljIHormiga, ljIIA {

    private String ljCoordenadaActual;

    public void ljSetCoordenadaActual(String ljCoord) {
        this.ljCoordenadaActual = ljCoord;
    }

    @Override
    public boolean ljComer(ljAlimento ljAlimento) {
        // Validación corregida: Ahora acepta Omnivoro
        if (ljAlimento.toString().equals("Omnivoro")) {
            return true;
        }
        return false;
    }

    @Override
    public boolean ljBuscar(String ljArsenal) {
        if (ljCoordenadaActual == null || ljArsenal == null)
            return false;

        // Cédula 1751330158 -> Digitos 5 y 8

        // Autómata Coord-05: Patrón abcdt+ (abcd seguido de al menos una t)
        if (ljCoordenadaActual.equals("Coord-05")) {
            if (ljArsenal.matches("abcdt+")) {
                return true;
            }
        }

        // Autómata Coord-08: Patrón abc (exacto)
        if (ljCoordenadaActual.equals("Coord-08")) {
            if (ljArsenal.equals("abc")) {
                return true;
            }
        }

        return false;
    }
}