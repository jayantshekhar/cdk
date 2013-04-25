/**
 * Copyright 2013 Cloudera Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloudera.cdk.examples.data;

import com.cloudera.data.Dataset;
import com.cloudera.data.DatasetReader;
import com.cloudera.data.DatasetRepository;
import com.cloudera.data.filesystem.FileSystemDatasetRepository;
import java.net.URI;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Read all the {@link User} objects from the users dataset.
 */
public class ReadUserDatasetPojo extends Configured implements Tool {

  @Override
  public int run(String[] args) throws Exception {

    // Construct a local filesystem dataset repository rooted at /tmp/data
    DatasetRepository repo = new FileSystemDatasetRepository.Builder()
        .rootDirectory(new URI("/tmp/data")).get();

    // Get the users dataset
    Dataset users = repo.get("users");

    // Get a reader for the dataset and read all the users
    DatasetReader<User> reader = users.getReader();
    try {
      reader.open();
      while (reader.hasNext()) {
        User user = reader.read();
        System.out.println(user);
      }
    } finally {
      reader.close();
    }

    return 0;
  }

  public static void main(String... args) throws Exception {
    int rc = ToolRunner.run(new ReadUserDatasetPojo(), args);
    System.exit(rc);
  }
}