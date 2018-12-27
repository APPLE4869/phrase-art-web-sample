-- ここからcategories関係

CREATE TABLE categories (
  id varchar(36) NOT NULL,
  name varchar(50) NOT NULL COMMENT 'カテゴリー名',
  image_path varchar(100) COMMENT 'イメージ画像パス',
  sequence int(11) NOT NULL COMMENT '順序',
  video_on_demand_associated tinyint(1) NOT NULL DEFAULT 0 COMMENT '動画配信サービスと関連があるかどうかを示すフラグ',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  PRIMARY KEY (id),
  UNIQUE KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='カテゴリー';

CREATE TABLE subcategories (
  id varchar(36) NOT NULL,
  category_id varchar(36) NOT NULL COMMENT 'カテゴリーID',
  name varchar(50) NOT NULL COMMENT 'サブカテゴリー名',
  image_path varchar(100) COMMENT 'イメージ画像パス',
  introduction varchar(500) COMMENT '紹介文',
  sequence int(11) NOT NULL COMMENT '順序',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  updated_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新日時',
  PRIMARY KEY (id),
  FOREIGN KEY (category_id) REFERENCES categories(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='サブカテゴリー';

CREATE TABLE video_on_demands (
  id varchar(36) NOT NULL,
  name_key varchar(20) NOT NULL COMMENT '名称キー',
  name varchar(50) NOT NULL COMMENT '名前',
  image_path varchar(100) NOT NULL COMMENT '画像URL',
  url varchar(255) NOT NULL COMMENT 'URL',
  app_deep_link varchar(255) COMMENT 'アプリのディープリンク',
  sequence int(11) NOT NULL COMMENT '順序',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  updated_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新日時',
  PRIMARY KEY (id),
  UNIQUE KEY (sequence)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='動画配信サービス';

CREATE TABLE subcategory_video_on_demands (
  id varchar(36) NOT NULL,
  subcategory_id varchar(36) NOT NULL COMMENT 'サブカテゴリーID',
  video_on_demand_id varchar(36) NOT NULL COMMENT '動画配信サービスID',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  PRIMARY KEY (id),
  UNIQUE (subcategory_id, video_on_demand_id),
  FOREIGN KEY (subcategory_id) REFERENCES subcategories(id),
  FOREIGN KEY (video_on_demand_id) REFERENCES video_on_demands(id),
  INDEX subcategory_id_index(subcategory_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='サブカテゴリーと動画配信サービスの中間テーブル';

-- ここまでcategories関係


-- ここからusers関係

CREATE TABLE users (
  id varchar(36) NOT NULL,
  username varchar(50) NOT NULL COMMENT 'ユーザー名',
  hashed_password varchar(255) NOT NULL COMMENT 'ハッシュ化されたパスワード',
  freezen tinyint(1) NOT NULL DEFAULT 0 COMMENT '凍結フラグ',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  updated_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新日時',
  PRIMARY KEY (id),
  UNIQUE KEY (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='ユーザー';

CREATE TABLE profiles (
  id varchar(36) NOT NULL,
  user_id varchar(36) NOT NULL COMMENT 'ユーザーID',
  image_path varchar(100) COMMENT '画像パス',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  updated_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新日時',
  PRIMARY KEY (id),
  UNIQUE KEY (user_id),
  FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='プロフィール';

-- ここまでusers関係


-- ここからphrases関係

-- phrasesのレコードは削除申請によって、deleteされることがあり得るので、その際一緒に消すテーブル以外のphrase_idにはFK制約をつけない。
CREATE TABLE phrases (
  id varchar(36) NOT NULL,
  category_id varchar(36) NOT NULL COMMENT 'カテゴリーID',
  subcategory_id varchar(36) COMMENT 'サブカテゴリーID',
  content varchar(500) NOT NULL COMMENT '内容',
  author_name varchar(50) NOT NULL COMMENT '作者の名前',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  updated_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新日時',
  PRIMARY KEY (id),
  FOREIGN KEY (category_id) REFERENCES categories(id),
  FOREIGN KEY (subcategory_id) REFERENCES subcategories(id),
  INDEX category_id_index(category_id),
  INDEX subcategory_id_index(subcategory_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='名言';

CREATE TABLE phrase_favorites (
  id varchar(36) NOT NULL,
  phrase_id varchar(36) NOT NULL COMMENT '名言ID',
  user_id varchar(36) NOT NULL COMMENT 'ユーザーID',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  PRIMARY KEY (id),
  UNIQUE (phrase_id, user_id),
  FOREIGN KEY (phrase_id) REFERENCES phrases(id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  INDEX phrase_id_index(phrase_id),
  INDEX user_id_index(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='名言のお気に入り登録';

CREATE TABLE phrase_likes (
  id varchar(36) NOT NULL,
  phrase_id varchar(36) NOT NULL COMMENT '名言ID',
  user_id varchar(36) NOT NULL COMMENT 'ユーザーID',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  PRIMARY KEY (id),
  UNIQUE (phrase_id, user_id),
  FOREIGN KEY (phrase_id) REFERENCES phrases(id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  INDEX phrase_id_index(phrase_id),
  INDEX user_id_index(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='名言へのいいね';

CREATE TABLE phrase_comments (
  id varchar(36) NOT NULL,
  phrase_id varchar(36) NOT NULL COMMENT '名言ID',
  user_id varchar(36) NOT NULL COMMENT 'ユーザーID',
  content varchar(500) NOT NULL COMMENT '内容',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  PRIMARY KEY (id),
  FOREIGN KEY (phrase_id) REFERENCES phrases(id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  INDEX phrase_id_index(phrase_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='名言へのコメント';

-- ここまでphrases関係


-- ここからupdate_requests関係

CREATE TABLE update_requests (
  id varchar(36) NOT NULL,
  user_id varchar(36) NOT NULL COMMENT 'ユーザーID',
  type varchar(50) NOT NULL COMMENT '種類',
  finished tinyint(1) NOT NULL COMMENT '完了フラグ',
  decision_expires_at datetime NOT NULL COMMENT '決定期限日時',
  final_decision_result varchar(10) COMMENT '最終判定結果',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  updated_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新日時',
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='更新申請';

CREATE TABLE update_request_decisions (
  id varchar(36) NOT NULL,
  user_id varchar(36) NOT NULL COMMENT 'ユーザーID',
  update_request_id varchar(36) NOT NULL COMMENT '更新申請ID',
  result varchar(10) NOT NULL COMMENT '結果',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  PRIMARY KEY (id),
  UNIQUE (user_id, update_request_id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (update_request_id) REFERENCES update_requests(id),
  INDEX update_request_id_index(update_request_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='更新申請の判定';

CREATE TABLE update_request_comments (
  id varchar(36) NOT NULL,
  update_request_id varchar(36) NOT NULL COMMENT 'サブカテゴリー更新申請ID',
  user_id varchar(36) NOT NULL COMMENT 'ユーザーID',
  content varchar(500) NOT NULL COMMENT '内容',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  PRIMARY KEY (id),
  FOREIGN KEY (update_request_id) REFERENCES update_requests(id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  INDEX update_request_id_index(update_request_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='更新申請へのコメント';

CREATE TABLE subcategory_modification_requests (
  id varchar(36) NOT NULL,
  update_request_id  varchar(36) NOT NULL COMMENT '更新申請ID',
  requested_subcategory_id varchar(36) NOT NULL COMMENT '申請されたサブカテゴリーID',
  requested_subcategory_name varchar(50) NOT NULL COMMENT '申請されたサブカテゴリー名',
  requested_subcategory_introduction varchar(500) COMMENT '申請されたサブカテゴリーの紹介文',
  requested_subcategory_image_path varchar(100) COMMENT '申請されたサブカテゴリーの画像パス',
  current_category_id varchar(36) NOT NULL COMMENT '現在のカテゴリーID',
  current_subcategory_name varchar(50) NOT NULL COMMENT '現在のサブカテゴリー名',
  current_subcategory_introduction varchar(500) COMMENT '現在のサブカテゴリーの紹介文',
  current_subcategory_image_path varchar(100) COMMENT '現在のサブカテゴリーの画像パス',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  PRIMARY KEY (id),
  FOREIGN KEY (update_request_id) REFERENCES update_requests(id),
  INDEX update_request_id_index(update_request_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='サブカテゴリー修正申請';

CREATE TABLE subcategory_modification_request_video_on_demands (
  id varchar(36) NOT NULL,
  update_request_id varchar(36) NOT NULL COMMENT 'サブカテゴリー修正申請ID',
  video_on_demand_id varchar(36) NOT NULL COMMENT '動画配信サービスID',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  PRIMARY KEY (id),
  UNIQUE (update_request_id, video_on_demand_id),
  FOREIGN KEY (update_request_id) REFERENCES update_requests(id),
  FOREIGN KEY (video_on_demand_id) REFERENCES video_on_demands(id),
  INDEX update_request_id_index(update_request_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='サブカテゴリー修正申請と動画配信サービスの中間テーブル';

CREATE TABLE phrase_registration_requests (
  id varchar(36) NOT NULL,
  update_request_id varchar(36) NOT NULL COMMENT '更新申請ID',
  requested_category_id varchar(36) NOT NULL COMMENT '申請されたカテゴリーID',
  requested_subcategory_id varchar(36) COMMENT '申請されたサブカテゴリーID',
  requested_subcategory_name varchar(50) COMMENT '申請されたサブカテゴリー名',
  requested_phrase_content varchar(500) NOT NULL COMMENT '申請された名言の内容',
  requested_phrase_author_name varchar(50) NOT NULL COMMENT '申請された名言の作者の名前',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  PRIMARY KEY (id),
  FOREIGN KEY (update_request_id) REFERENCES update_requests(id),
  FOREIGN KEY (requested_category_id) REFERENCES categories(id),
  INDEX update_request_id_index(update_request_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='名言登録申請';

CREATE TABLE phrase_modification_requests (
  id varchar(36) NOT NULL,
  update_request_id varchar(36) NOT NULL COMMENT '更新申請ID',
  requested_phrase_id varchar(36) NOT NULL COMMENT '申請された名言ID',
  requested_category_id varchar(36) NOT NULL COMMENT '申請されたカテゴリーID',
  requested_subcategory_id varchar(36) COMMENT '申請されたサブカテゴリーID',
  requested_subcategory_name varchar(50) COMMENT '申請されたサブカテゴリー名',
  requested_phrase_content varchar(500) NOT NULL COMMENT '申請された名言の内容',
  requested_phrase_author_name varchar(50) NOT NULL COMMENT '申請された名言の作者の名前',
  current_category_id varchar(36) NOT NULL COMMENT '現在のカテゴリーID',
  current_subcategory_id varchar(36) COMMENT '現在のサブカテゴリーID',
  current_subcategory_name varchar(36) COMMENT '現在のサブカテゴリー名',
  current_phrase_content varchar(500) NOT NULL COMMENT '現在の名言の内容',
  current_phrase_author_name varchar(50) NOT NULL COMMENT '現在の名言の作者の名前',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  PRIMARY KEY (id),
  FOREIGN KEY (update_request_id) REFERENCES update_requests(id),
  FOREIGN KEY (requested_category_id) REFERENCES categories(id),
  FOREIGN KEY (current_category_id) REFERENCES categories(id),
  INDEX update_request_id_index(update_request_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='名言修正申請';

CREATE TABLE phrase_deletion_requests (
  id varchar(36) NOT NULL,
  update_request_id  varchar(36) NOT NULL COMMENT '更新申請ID',
  requested_phrase_id varchar(36) NOT NULL COMMENT '申請された名言ID',
  current_category_id varchar(36) NOT NULL COMMENT '現在のカテゴリーID',
  current_subcategory_id varchar(36) COMMENT '現在のサブカテゴリーID',
  current_subcategory_name varchar(36) COMMENT '現在のサブカテゴリー名',
  current_phrase_content varchar(500) NOT NULL COMMENT '現在の名言の内容',
  current_phrase_author_name varchar(50) NOT NULL COMMENT '現在の名言の作者の名前',
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '作成日時',
  PRIMARY KEY (id),
  FOREIGN KEY (update_request_id) REFERENCES update_requests(id),
  FOREIGN KEY (current_category_id) REFERENCES categories(id),
  INDEX update_request_id_index(update_request_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='名言削除申請';

-- ここまでupdate_requests関係
