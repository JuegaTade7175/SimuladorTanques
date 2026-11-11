# Configuración de ProGuard para ofuscar el proyecto

-injars Dist/SimuladorTanques.exe
-outjars Dist/SimuladorTanques_ofuscado.exe

-libraryjars lib/*

-keep public class com.ejercito.simulador.SimuladorPrincipal {
    public static void main(java.lang.String[]);
}

-dontoptimize
-dontshrink
