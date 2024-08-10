ALTER TABLE sprints ADD COLUMN project_id int NOT NULL;
ALTER TABLE sprints ADD CONSTRAINT FK_Project FOREIGN KEY(project_id) REFERENCES projects(id);