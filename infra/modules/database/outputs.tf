output "mongodb_uri" {
  description = "MongoDB URI"
  value = "mongodb+srv://${var.db_username}:${var.db_password}@${replace(mongodbatlas_cluster.cluster.connection_strings[0].standard_srv, "mongodb+srv://", "")}?retryWrites=true&w=majority"
  sensitive = true
}
