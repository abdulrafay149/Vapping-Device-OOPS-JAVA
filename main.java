abstract class Component {
    protected String name;

    public Component(String name) {
        this.name = name;
    }

    public abstract void operate();
}

class HeatingCoil extends Component implements Runnable {
    public HeatingCoil() {
        super("Heating Coil");
    }

    @Override
    public void run() {
        operate();
    }

    @Override
    public void operate() {
        System.out.println(name + ": Heating up...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(name + " interrupted.");
        }
        System.out.println(name + ": Reached optimal temperature.");
    }
}


class Vaporizer extends Component implements Runnable {
    public Vaporizer() {
        super("Vaporizer");
    }

    @Override
    public void run() {
        operate();
    }

    @Override
    public void operate() {
        System.out.println(name + ": Generating vapor...");
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            System.out.println(name + " interrupted.");
        }
        System.out.println(name + ": Vaporization complete.");
    }
}


class Battery {
    public void powerOn() {
        System.out.println("Battery: Power ON.");
    }

    public void powerOff() {
        System.out.println("Battery: Power OFF.");
    }
}


class Tank {
    public void fill() {
        System.out.println("Tank: Filled with e-liquid.");
    }

    public void empty() {
        System.out.println("Tank: E-liquid used.");
    }
}


class UserControl {
    public void activate() {
        System.out.println("UserControl: Activating vape...");
    }

    public void deactivate() {
        System.out.println("UserControl: Deactivating vape.");
    }
}


class VapingDevice {
    private HeatingCoil coil;
    private Vaporizer vaporizer;
    private Battery battery;
    private Tank tank;
    private UserControl control;

    public VapingDevice(UserControl control) {
        this.coil = new HeatingCoil();
        this.vaporizer = new Vaporizer();
        this.battery = new Battery();
        this.tank = new Tank();
        this.control = control;
    }

    public void startSession() {
        battery.powerOn();
        tank.fill();
        control.activate();

        Thread coilThread = new Thread(coil);
        Thread vaporThread = new Thread(vaporizer);

        coilThread.start();
        vaporThread.start();

        try {
            coilThread.join();
            vaporThread.join();
        } catch (InterruptedException e) {
            System.out.println("Vape session interrupted.");
        }

        control.deactivate();
        tank.empty();
        battery.powerOff();
    }
}


public class Main {
    public static void main(String[] args) {
        UserControl userControl = new UserControl();
        VapingDevice vape = new VapingDevice(userControl);
        vape.startSession();
    }
}
