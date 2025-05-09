variable "atlas_public_key" {
  description = "MongoDB Atlas Public API Key"
  type        = string
}

variable "atlas_private_key" {
  description = "MongoDB Atlas Private API Key"
  type        = string
  sensitive   = true
}

variable "atlas_org_id" {
  description = "MongoDB Atlas Organization ID"
  type        = string
}

variable "project_name" {
  description = "Name of the MongoDB Atlas project"
  type        = string
  default     = "MyMongoDBProject"
}

variable "cluster_name" {
  description = "Name of the MongoDB Atlas cluster"
  type        = string
  default     = "MyMOCluster"
}

variable "db_username" {
  description = "Database username"
  type        = string
  default     = "dbAdmin"
}

variable "db_password" {
  description = "Database user password"
  type        = string
  sensitive   = true
}
