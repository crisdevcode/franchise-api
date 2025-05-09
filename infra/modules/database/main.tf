terraform {
  required_providers {
    mongodbatlas = {
      source = "mongodb/mongodbatlas"
      version = "1.34.0"
    }
  }
}

provider "mongodbatlas" {
  public_key  = var.atlas_public_key
  private_key = var.atlas_private_key
}

# Create a MongoDB Atlas Project
resource "mongodbatlas_project" "project" {
  name   = var.project_name
  org_id = var.atlas_org_id
}

# Deploy an M0 Free Tier Cluster
resource "mongodbatlas_cluster" "cluster" {
  project_id               = mongodbatlas_project.project.id
  name                     = var.cluster_name
  provider_name            = "TENANT" # Required for M0 Free Tier
  backing_provider_name    = "AWS"
  provider_region_name     = "US_EAST_1"
  cluster_type             = "REPLICASET"
  disk_size_gb             = 0.5
  mongo_db_major_version   = "8.0"
  provider_instance_size_name = "M0"
}

# Create a Database User
resource "mongodbatlas_database_user" "db_user" {
  username           = var.db_username
  password           = var.db_password
  project_id         = mongodbatlas_project.project.id
  auth_database_name = "admin"

  roles {
    role_name     = "readWriteAnyDatabase"
    database_name = "admin"
  }
}

# Whitelist an IP Address (0.0.0.0/0 allows all)
resource "mongodbatlas_project_ip_access_list" "whitelist" {
  project_id = mongodbatlas_project.project.id
  cidr_block = "0.0.0.0/0"
  comment    = "Allow all IPs (not recommended for production)"
}
