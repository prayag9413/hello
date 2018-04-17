import socket
from pycipher import Playfair


s=socket.socket()
host="127.0.0.1"
port=12358
s.connect((host,port))


msg=str(input("Password: "))
msg=Playfair("cdabefhgkijlmnoprqstuvzwxy").encipher(msg)
print(msg)
s.send(bytes(msg,'utf-8'))
s.close()

"""
Output
===============

➜  A6 python3 server.py
addr :  ('127.0.0.1', 51571)
password successful
addr :  ('127.0.0.1', 51572)
wrong password



➜  A6 python3 client.py
Password: hello
IDOVTE
➜  A6 python3 client.py
Password: world
XNVREV

"""