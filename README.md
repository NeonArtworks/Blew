# Blew
Blew is an easy to use Java-Android Bluetooth-Wrapper.

# How To
Below is a sample program which shows the basic features of Blew.
```java
public static void main(String[] args) 
{
  Blew blew = new Blew(); // make sure that bluetooth is enabled on your device!

  // map -> key: Device name, value: Device address
  Map<String, String> addr = blew.getPairedDevices();
  boolean connected = blew.OpenConnection(addr.get("[MY-DEVICE-NAME]")); // tries to connect to the device
	
  // connected can also be aquired via : blew.isConnected();
  if (connected) 
  {
    blew.sendData("Hello World".getBytes()); // sends Hello World to the connected device
    blew.closeConnection(); // closes the connection
  }
}

```
