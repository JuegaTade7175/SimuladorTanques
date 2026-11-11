package com.ejercito.simulador;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

public class SimuladorPrincipal extends SimpleApplication implements ActionListener {
    
    private Geometry tanqueGeom;
    private boolean adelante = false;
    private boolean atras = false;
    private boolean izquierda = false;
    private boolean derecha = false;
    private float velocidad = 5f;
    private float rotacion = 2f;
    
    public static void main(String[] args) {
        new SimuladorPrincipal().start();
    }
    
    @Override
    public void simpleInitApp() {
        // Configurar controles de teclado
        inputManager.addMapping("Adelante", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Atras", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Izquierda", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Derecha", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(this, "Adelante", "Atras", "Izquierda", "Derecha");
        
        // Crear suelo
        Box suelo = new Box(50, 0.1f, 50);
        Geometry sueloGeom = new Geometry("Suelo", suelo);
        Material sueloMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        sueloMat.setColor("Color", ColorRGBA.Gray);
        sueloGeom.setMaterial(sueloMat);
        sueloGeom.setLocalTranslation(0, -0.5f, 0);
        rootNode.attachChild(sueloGeom);
        
        // Crear un cubo como tanque temporal
        Box tanque = new Box(1, 0.5f, 2);
        tanqueGeom = new Geometry("Tanque", tanque);
        
        // Material verde para el tanque
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        tanqueGeom.setMaterial(mat);
        
        rootNode.attachChild(tanqueGeom);
        
        // Agregar luz direccional
        DirectionalLight sol = new DirectionalLight();
        sol.setDirection(new Vector3f(-0.5f, -1f, -0.5f).normalizeLocal());
        sol.setColor(ColorRGBA.White);
        rootNode.addLight(sol);
        
        // Configurar cámara
        cam.setLocation(new Vector3f(0, 5, 10));
        cam.lookAt(new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));
        
        System.out.println("Simulador 3D iniciado");
        System.out.println("Controles: W/A/S/D para mover el tanque");
        System.out.println("Presiona ESC para salir");
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        // Movimiento del tanque
        Vector3f posicion = tanqueGeom.getLocalTranslation();
        float[] rotacionTanque = tanqueGeom.getLocalRotation().toAngles(null);
        
        if (adelante) {
            float angulo = rotacionTanque[1];
            posicion.x -= (float) Math.sin(angulo) * velocidad * tpf;
            posicion.z -= (float) Math.cos(angulo) * velocidad * tpf;
        }
        if (atras) {
            float angulo = rotacionTanque[1];
            posicion.x += (float) Math.sin(angulo) * velocidad * tpf;
            posicion.z += (float) Math.cos(angulo) * velocidad * tpf;
        }
        if (izquierda) {
            tanqueGeom.rotate(0, rotacion * tpf, 0);
        }
        if (derecha) {
            tanqueGeom.rotate(0, -rotacion * tpf, 0);
        }
        
        tanqueGeom.setLocalTranslation(posicion);
        
        // Cámara sigue al tanque
        cam.setLocation(posicion.add(0, 5, 10));
        cam.lookAt(posicion, Vector3f.UNIT_Y);
    }
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        switch (name) {
            case "Adelante":
                adelante = isPressed;
                break;
            case "Atras":
                atras = isPressed;
                break;
            case "Izquierda":
                izquierda = isPressed;
                break;
            case "Derecha":
                derecha = isPressed;
                break;
        }
    }
    
    @Override
    public void simpleRender(RenderManager rm) {
        // Aquí irá la lógica de renderizado personalizado si es necesario
    }
}
