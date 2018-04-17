import socket
from pycipher import Playfair

s=socket.socket()
host="127.0.0.1"
port=12358
s.bind((host,port))
s.listen(5)
while True:
	c, a=s.accept()
	print ("addr : ",a)
	rcv_pwd = c.recv(1024)
	rcv_pwd = str(rcv_pwd.decode("utf-8"))
	if(Playfair("cdabefhgkijlmnoprqstuvzwxy").encipher("hello") == rcv_pwd):
		print("password successful")
	else:
		print("wrong password ")
	c.close()
