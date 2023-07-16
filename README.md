# Realtime Data Migration DataBase

This repository contains a solution for migrating realtime data from the `contact` table to the `recent_contact` table while minimizing the chances of deadlock occurrences. The purpose of this migration is to ensure that the `recent_contact` table always contains the most up-to-date and relevant contact information.

## Table of Contents
- [Introduction](#introduction)
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)

## Introduction

In my current project, there is a need to migrate data from the `contact` table to the `recent_contact` table. Initially, we attempted to use the SQL statement.

```sql
INSERT INTO recent_contact (profile_id, contact_id)
SELECT profile_id, id FROM contact;
```

However, this resulted in deadlocks that adversely affected system operations. To overcome this challenge, I have developed a solution to eliminate deadlocks during the database migration process.

This repository offers an improved approach for successfully migrating data from the contact table to the recent_contact table without encountering deadlocks. By implementing effective concurrency control techniques, optimizing read and write operations, and utilizing appropriate transaction isolation levels, we ensure a smooth and reliable migration process.

The solution provided here serves as a comprehensive guide, providing detailed instructions to set up prerequisites, install the migration script, and configure the migration settings specific to your environment. By following these guidelines, you can migrate data seamlessly and prevent any disruptions caused by deadlocks.

Next, we will provide detailed information on getting started with the migration process. If you have any questions or need assistance, please don't hesitate to ask.

