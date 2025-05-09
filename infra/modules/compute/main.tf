provider "aws" {
  region = "us-east-1"
}

resource "aws_security_group" "security_group" {
  name        = "security_group"
  description = "Allow HTTP access and Internet access"

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    description = "Public HTTP access"
  }

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    description = "SSH access restricted"
  }

  egress {
    from_port   = 27017
    to_port     = 27017
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    description = "Connection to MongoDB Atlas"
  }

  egress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    description = "Outbound HTTPS connections"
  }

}

resource "aws_key_pair" "keypair" {
  key_name = "terraform-keypair"
  public_key = file("~/.ssh/id_ed25519.pub")
}

resource "aws_instance" "server" {
  ami           = "ami-0f88e80871fd81e91"
  instance_type = "t3.micro"
  key_name      = aws_key_pair.keypair.key_name
  vpc_security_group_ids = [aws_security_group.security_group.id]

  user_data = templatefile("${path.root}/user_data.sh", {
    mongodb_uri = var.mongodb_uri
  })

}
