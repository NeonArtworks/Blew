package at.neonartworks.blew;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

/**
 * <h1>Blew</h1> <br>
 * <p>
 * BLEW is an easy to use Java-Android Bluetooth-Wrapper. Created by Florian
 * Wagner on 06.02.2018.
 */

public class Blew {
	private BluetoothAdapter myBluetooth = null;
	private BluetoothSocket btSocket = null;
	private boolean isBtConnected = false;
	private final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private boolean ConnectSuccess = true; // if it's here, it's almost connected
	private Set<BluetoothDevice> pairedDevices;
	private Map<String, String> bList;

	/**
	 * <h1>Blew</h1>
	 * <p>
	 * Constructor. When you call this method, make sure, that Bluetooth is enabled
	 * on your device, otherwise your App may fail!
	 * </p>
	 */
	public Blew() {
		myBluetooth = BluetoothAdapter.getDefaultAdapter();
	}

	/**
	 * <h1>public {@link Boolean} sendData({@link Byte}byte[] data)</h1>
	 * <p>
	 * Sends the data to the connected device. It checks whether a device is
	 * connected or not! If send successfully 'TRUE' will be returend, otherwise
	 * 'FALSE'.
	 * </p>
	 * 
	 * @param data
	 *            the data to send
	 * @return true on success, otherwise false
	 */
	public boolean sendData(byte[] data) {
		try {
			if (btSocket.isConnected()) {
				btSocket.getOutputStream().write(data);
				return true;
			}
		} catch (IOException e) {
		}
		return false;
	}

	/**
	 * <h1>public {@link Boolean} sendData({@link byte} data)</h1>
	 * <p>
	 * Sends the data to the connected device. It checks whether a device is
	 * connected or not! If send successfully 'TRUE' will be returend, otherwise
	 * 'FALSE'.
	 * </p>
	 * 
	 * @param data
	 *            the data to send
	 * @return true on success, otherwise false
	 */
	public boolean sendData(byte data) {
		try {
			if (btSocket.isConnected()) {
				btSocket.getOutputStream().write(data);
				return true;
			}
		} catch (IOException e) {
		}
		return false;
	}

	/**
	 * <h1>public {@link Boolean} isEnabled()</h1>
	 * <p>
	 * Checks if bluetooth is enabled on the device.
	 * </p>
	 * 
	 * @return wether the device is enabled or not
	 */
	public boolean isEnabled() {
		return myBluetooth.isEnabled();
	}

	/**
	 * <h1>public {@link Map} getPairedDevices()</h1>
	 * 
	 * <p>
	 * Returns all addresses of all paired devices. The Key value is the device
	 * name, the Value is the device' address.
	 * </p>
	 * 
	 * @return a map with the name and address of the device
	 */
	public Map<String, String> getPairedDevices() {
		if (myBluetooth.isEnabled()) {
			// Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			/// startActivityForResult(getVisible, 0);
			pairedDevices = myBluetooth.getBondedDevices();
			bList = new HashMap<String, String>();
			for (BluetoothDevice bt : pairedDevices) {
				bList.put(bt.getName(), bt.getAddress());
			}
		}
		return bList;
	}

	/**
	 * <h1>public void closeConnection()</h1>
	 * <p>
	 * closes the connection to the device.
	 * </p>
	 */
	public void closeConnection() {
		try {
			btSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <h1>public {@link Boolean} OpenConnection({@link String} address);</h1>
	 * <p>
	 * Opens an connection to the device given with address. The address can be
	 * aquired with the {@link Blew#getAddress}. This method returns 'TRUE' if the
	 * connection was successful otherwise 'FALSE'.
	 * 
	 * @param address
	 *            the address of the device to connect to
	 * 
	 *            </p>
	 */
	public boolean OpenConnection(String address) {
		try {
			if (btSocket == null || !isBtConnected) {
				myBluetooth = BluetoothAdapter.getDefaultAdapter();// get the mobile bluetooth device
				BluetoothDevice blueDevice = myBluetooth.getRemoteDevice(address);// connects to the device's address
																					// and checks if it's available
				btSocket = blueDevice.createInsecureRfcommSocketToServiceRecord(uuid);// create a RFCOMM (SPP)
																						// connection
				BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
				btSocket.connect();// start connection
				ConnectSuccess = true;
			}
		} catch (IOException e) {
			ConnectSuccess = false;// if the try failed, you can check the exception here
		}
		isBtConnected = ConnectSuccess;
		return isConnected();
	}

	/**
	 * <h>public {@link Boolean} isConnected()</h>
	 * <p>
	 * Checks if a bluetooth device is connected. If no bluetooth device is
	 * connected to your smartphone it will return false.
	 * </p>
	 * 
	 * @return true or false depending if a device is connected.
	 */
	public boolean isConnected() {
		return isBtConnected;
	}

}