var express = require("express");
var app = express();
var server = require("http").createServer(app);
var io = require("socket.io").listen(server);
var fs = require("fs");
server.listen(process.env.PORT || 3000); // server lắng nge trong port 3000

//app.get("/", function(req, res){
//	res.sendFile(__dirname + "/index.html");	// test file index.html là file mặc định được mở 
//}); // 3 dọng 8,9,10 chỉ để test server lúc tạo thôi

var arrayUser = []; // tạo mang chứa user
var isTontai = true;
console.log("Server running...");

// Nhận dữ liệu và nhận kết nối
io.sockets.on('connection', function(socket){ // socket chứa thông tin thiết bị ,connection là sự kiện mặc đinh
     console.log("Co thiet bi vua ket noi!");

     //Nhận dữ liệu chat
     socket.on('client-send-chat',function(messeage){ // tham số gồm tên sự kiện lấy bên adroid và function lấy dữ liệu
     	console.log("Server nhận : "+ socket.un+ messeage);

     	// server tin vừa nhận được về máy
     	io.sockets.emit('server-send-chat',{noidung: socket.un + " : " +messeage}) // dữ dữ liệu vừa nhận về tất cả các máy dạng key - values với value là json object
     	// socket.emit() : gửi cho máy vừa gửi lên

     }); 

     // Nhận user
     socket.on('client-register-user',function(data){
     	// Kiểm tra user gửi lên đã có hay chưa
     	// nếu chưa có -> đăng kí
     	if(arrayUser.indexOf(data) == -1){ 
     		arrayUser.push(data);
     		isTontai = false;
     		console.log("User đăng kí : " + data);
     		// gán tên user cho socket đã thêm ở trên
     		socket.un = data;

     		// Gửi danh sách người dùng đăng kí thành công về 
     		io.sockets.emit('server-send-danhsach',{danhsach : arrayUser});

     	}else{
     		console.log("User đã tồn tại");
     		isTontai = true;
     	}
     	// gửi kq đăng kí thành công hay ko cho user
     	socket.emit('server-send-result',{ketqua:isTontai});

     });
}); 

