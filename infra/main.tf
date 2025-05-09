module "database_module" {
  source = "./modules/database"

  atlas_public_key  = var.atlas_public_key
  atlas_private_key = var.atlas_private_key

  atlas_org_id      = var.atlas_org_id
  cluster_name      = var.cluster_name
  project_name      = var.project_name

  db_username       = var.db_username
  db_password       = var.db_password
}

module "compute_module" {
  source = "./modules/compute"
  mongodb_uri = module.database_module.mongodb_uri
}

output "mongodb_uri" {
  value = module.database_module.mongodb_uri
  sensitive = true
}