--
-- Table structure for table `admin_info`
--

DROP TABLE IF EXISTS admin_info;

CREATE TABLE admin_info (
    admin_id VARCHAR(100) NOT NULL PRIMARY KEY COMMENT '主キー、管理者の一意なID',
    admin_name VARCHAR(100) NOT NULL COMMENT '管理者の名前',
    mail VARCHAR(320) NOT NULL COMMENT '管理者のメールアドレス',
    phone_number VARCHAR(20) NOT NULL COMMENT '管理者の電話番号',
    password VARCHAR(255) NOT NULL COMMENT 'ログインパスワード',
    delete_flag TINYINT(1) NOT NULL DEFAULT 0 COMMENT '論理削除フラグ (0:未削除, 1:削除済)',
    create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作が行われた日時',
    update_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作が行われた日時 (create_dateと一致)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理者情報テーブル';

INSERT INTO admin_info VALUES ('amoibeojt','運営事務局','sample.com','090××××××××','$2a$10$lC4aebTI9REPrc0c5mxJU.uGd1GvzG.wPcHTN5oxTEB3jt23P.0fW',0,'2024-01-23 00:00:00','2024-01-23 00:00:00'),('tokyo_operater','[東京]オペレータ','sample.com','090××××××××','$2a$10$lC4aebTI9REPrc0c5mxJU.uGd1GvzG.wPcHTN5oxTEB3jt23P.0fW',0,'2024-01-23 00:00:00','2024-01-23 00:00:00');

--
-- Table structure for table `center_info`
--

DROP TABLE IF EXISTS center_info;

CREATE TABLE center_info (
    center_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主キー、在庫センターの一意なID',
    center_name VARCHAR(20) NOT NULL COMMENT '在庫センター名',
    post_code VARCHAR(10) NOT NULL COMMENT '在庫センターの郵便番号',
    address VARCHAR(255) NOT NULL COMMENT '在庫センターの住所',
    phone_number VARCHAR(20) NOT NULL COMMENT '在庫センターの管理者の電話番号',
    manager_name VARCHAR(100) NOT NULL COMMENT '在庫センターの管理者名',
    operational_status TINYINT(1) NOT NULL DEFAULT 0 COMMENT '稼働ステータス (0:稼働中, 1:停止)',
    max_storage_capacity VARCHAR(10) NOT NULL COMMENT '最大保管容量 (m³)',
    current_storage_capacity VARCHAR(10) NOT NULL COMMENT '現在保管容量 (m³)',
    notes VARCHAR(255) NULL COMMENT '補足',
    delete_flag TINYINT(1) NOT NULL DEFAULT 0 COMMENT '論理削除フラグ',
    create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時',
    update_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='在庫センター情報テーブル';

INSERT INTO center_info VALUES (1,'メインセンター','100-0001','東京都千代田区千代田1-1','03-1234-5678','山田太郎','0',1000,500,NULL,0,'2025-07-15 22:29:18','2025-07-15 22:29:18'),(2,'サテライトセンター','150-0002','東京都渋谷区渋谷2-2-2','03-8765-4321','佐藤花子','0',500,250,NULL,0,'2025-07-15 22:29:18','2025-07-15 22:29:18'),(3,'名古屋センター','450-0002','愛知県名古屋市中村区名駅4-4-4','052-000-1111','佐藤次郎','1',600,0,'設備メンテナンス中',0,'2025-07-12 07:46:38','2025-07-12 07:46:38'),(4,'東京物流センター','105-0000','東京都港区','03-1234-5678','田中 太郎','0',500,350,NULL,0,'2025-07-17 20:31:18','2025-07-17 20:31:18'),(5,'大阪物流センター','530-0000','大阪府大阪市','06-8765-4321','鈴木 一郎','0',300,250,NULL,0,'2025-07-17 20:31:18','2025-07-17 20:31:18'),(6,'名古屋物流センター','460-0000','愛知県名古屋市','052-123-4567','佐藤 花子','0',600,250,NULL,0,'2025-07-17 20:31:18','2025-07-17 20:31:18'),(7,'仙台物流センター','980-0000','宮城県仙台市','022-234-5678','中田 太郎','1',400,300,NULL,0,'2025-07-17 20:31:18','2025-07-17 20:31:18'),(8,'福岡物流センター','810-0000','福岡県福岡市','092-234-5678','近藤 一郎','0',350,150,NULL,0,'2025-07-17 20:31:18','2025-07-17 20:31:18'),(9,'北海道物流センター','060-0000','北海道札幌市','011-234-5678','小池 花子','0',1200,250,NULL,0,'2025-07-17 20:31:18','2025-07-17 20:31:18'),(10,'静岡物流センター','420-0000','静岡県静岡市','054-123-4567','中田 太郎','1',400,300,NULL,0,'2025-07-17 20:31:18','2025-07-17 20:31:18'),(11,'山梨物流センター','400-0000','山梨県山梨市','055-123-4567','小山 勇気','0',400,300,NULL,0,'2025-07-17 20:31:18','2025-07-17 20:31:18');

--
-- Table structure for table `parts_category_info`
--

DROP TABLE IF EXISTS parts_category_info;

CREATE TABLE parts_category_info (
    category_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主キー、部品カテゴリの一意なID',
    category_name VARCHAR(20) NOT NULL COMMENT '部品カテゴリ名称',
    delete_flag TINYINT(1) NOT NULL DEFAULT 0 COMMENT '論理削除フラグ',
    create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時',
    update_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部品カテゴリ情報テーブル';

INSERT INTO parts_category_info VALUES (1,'フレーム',0,'2025-07-15 22:29:18','2025-07-15 22:29:18'),(2,'モーター',0,'2025-07-15 22:29:18','2025-07-15 22:29:18'),(3,'プロペラ',0,'2025-07-15 22:29:18','2025-07-15 22:29:18'),(4,'レンズ',0,'2025-07-15 22:29:18','2025-07-15 22:29:18'),(5,'アーム',0,'2025-07-15 22:29:18','2025-07-15 22:29:18');

--
-- Table structure for table `parts_stock`
--

DROP TABLE IF EXISTS parts_stock;

CREATE TABLE parts_stock (
    stock_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主キー、部品在庫の一意なID',
    category_id INT NOT NULL COMMENT '部品カテゴリID',
    name VARCHAR(255) NOT NULL COMMENT '部品名',
    center_id INT NOT NULL COMMENT '在庫センターID',
    description VARCHAR(255) NULL COMMENT '説明',
    amount INT NOT NULL DEFAULT 0 COMMENT '数量',
    delete_flag TINYINT(1) NOT NULL DEFAULT 0 COMMENT '論理削除フラグ',
    create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時',
    update_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    CONSTRAINT fk_parts_stock_category FOREIGN KEY (category_id) REFERENCES parts_category_info(category_id),
    CONSTRAINT fk_parts_stock_center FOREIGN KEY (center_id) REFERENCES center_info(center_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部品在庫テーブル';

INSERT INTO parts_stock VALUES (1,1,'ドローン',1,'ドローンの部品',50,0,'2025-07-12 07:45:26','2025-07-12 19:18:05'),(2,2,'歯車ユニット',2,'減速用ギア一式',200,0,'2025-07-12 07:45:26','2025-07-12 07:45:26'),(3,3,'プラスチックカバー',3,'ABS 樹脂製外装カバー',0,0,'2025-07-12 07:45:26','2025-07-12 07:45:26'),(4,1,'加速度センサモジュール',2,'3軸加速度センサ',150,0,'2025-07-12 07:45:26','2025-07-12 07:45:26'),(5,2,'ボールベアリング',1,'精密深溝玉軸受',350,0,'2025-07-12 07:45:26','2025-07-12 07:45:26'),(6,1,'スマホ用マイコン',1,'ARM Cortex-M 系マイコン',500,0,'2025-07-12 07:45:26','2025-07-12 19:18:05');

--
-- Table structure for table `products_category_info`
--

DROP TABLE IF EXISTS products_category_info;

CREATE TABLE products_category_info (
    category_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主キー、製品カテゴリの一意なID',
    category_name VARCHAR(20) NOT NULL COMMENT '製品カテゴリ名称',
    delete_flag TINYINT(1) NOT NULL DEFAULT 0 COMMENT '論理削除フラグ',
    create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時',
    update_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='製品カテゴリ情報テーブル';

INSERT INTO products_category_info VALUES (1,'完成ドローン',0,'2025-07-15 10:00:00','2025-07-15 10:00:00'),(2,'センサーモジュール',0,'2025-07-15 10:00:00','2025-07-15 10:00:00'),(3,'コントローラー',0,'2025-07-15 10:00:00','2025-07-15 10:00:00'),(4,'バッテリーキット',0,'2025-07-15 10:00:00','2025-07-15 10:00:00'),(5,'急速充電器',0,'2025-07-15 10:00:00','2025-07-15 10:00:00');

--
-- Table structure for table `products_stock`
--

DROP TABLE IF EXISTS products_stock;

CREATE TABLE products_stock (
    stock_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主キー、製品在庫の一意なID',
    category_id INT NOT NULL COMMENT '製品カテゴリID',
    name VARCHAR(255) NOT NULL COMMENT '製品名',
    center_id INT NOT NULL COMMENT '在庫センターID',
    description VARCHAR(255) NULL COMMENT '説明',
    amount INT NOT NULL DEFAULT 0 COMMENT '数量',
    delete_flag TINYINT(1) NOT NULL DEFAULT 0 COMMENT '論理削除フラグ',
    create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時',
    update_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    CONSTRAINT fk_products_stock_category FOREIGN KEY (category_id) REFERENCES products_category_info(category_id),
    CONSTRAINT fk_products_stock_center FOREIGN KEY (center_id) REFERENCES center_info(center_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='製品在庫テーブル';

INSERT INTO products_stock VALUES (1,1,'ドローン A セット',1,'完成品ドローンA＋予備バッテリー',10,0,'2025-07-01 09:00:00','2025-07-10 12:00:00'),(2,2,'GPS センサーモジュール',2,'高精度 GPS モジュール',50,0,'2025-07-02 10:30:00','2025-07-11 13:15:00'),(3,3,'メインコントローラー基板',3,'ドローン制御用メイン基板',25,0,'2025-07-03 11:45:00','2025-07-12 14:30:00'),(4,4,'バッテリーキット（5000mAh）',1,'長時間飛行用バッテリー×2本',30,0,'2025-07-04 13:00:00','2025-07-13 15:45:00'),(5,5,'急速充電器 QC3.0 対応',2,'2ポート急速充電器',75,0,'2025-07-05 14:15:00','2025-07-14 16:00:00');

--
-- Table structure for table `parts_stock_history`
--

DROP TABLE IF EXISTS parts_stock_history;

CREATE TABLE parts_stock_history (
    history_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主キー、履歴ID',
    stock_id INT NOT NULL COMMENT '部品在庫ID',
    transaction_type VARCHAR(20) NOT NULL COMMENT '取引種別',
    transaction_date DATETIME NOT NULL COMMENT '処理日時',
    amount_before INT NOT NULL COMMENT '処理前数量',
    amount_change INT NOT NULL COMMENT '変動数量',
    amount_after INT NOT NULL COMMENT '処理後数量',
    supplier_name VARCHAR(100) NULL COMMENT '仕入先企業名',
    purchase_order_no VARCHAR(50) NULL COMMENT '発注書番号',
    operator_name VARCHAR(50) NULL COMMENT '処理担当者名',
    lot_number VARCHAR(50) NULL COMMENT 'ロット番号',
    remarks VARCHAR(255) NULL COMMENT '備考',
    delete_flag TINYINT(1) NOT NULL DEFAULT 0 COMMENT '論理削除フラグ',
    create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時',
    update_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    CONSTRAINT fk_parts_stock_history_stock FOREIGN KEY (stock_id) REFERENCES parts_stock(stock_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部品入出庫履歴テーブル';

INSERT INTO parts_stock_history VALUES
  (1, 1, 'RECEIVE',         '2025-07-01 09:00:00',  20,  10, 30, 'サプライヤA', 'PO1001', '山田太郎', 'LOT-A-001', '初回入荷',       0, '2025-07-01 09:01:00', '2025-07-01 09:01:00'),
  (2, 1, 'PRODUCTION_OUT',  '2025-07-02 10:30:00',  30,  -5, 25, NULL,         NULL,      '佐藤花子', 'LOT-P-001', '製造出庫分',     0, '2025-07-02 10:31:00', '2025-07-02 10:31:00'),
  (3, 2, 'ADJUSTMENT',      '2025-07-03 11:45:00',  50,   5, 55, NULL,         NULL,      '高橋次郎', NULL,         '在庫調整',       0, '2025-07-03 11:46:00', '2025-07-03 11:46:00'),
  (4, 2, 'RECEIVE_CANCEL',  '2025-07-04 13:00:00',  55,  -5, 50, 'サプライヤB', 'PO1002', '伊藤美咲', 'LOT-C-001', '入荷取消',       0, '2025-07-04 13:01:00', '2025-07-04 13:01:00'),
  (5, 3, 'PRODUCTION_CANCEL','2025-07-05 14:15:00',  60,   0, 60, NULL,         NULL,      '中村浩',   'LOT-P-002', '製造取消',       0, '2025-07-05 14:16:00', '2025-07-05 14:16:00'),
  (6, 4, 'RECEIVE',         '2025-07-06 15:30:00',  15,  10, 25, 'サプライヤC', 'PO1003', '田中次郎', 'LOT-A-002', '定期入荷',       0, '2025-07-06 15:31:00', '2025-07-06 15:31:00'),
  (7, 5, 'PRODUCTION_OUT',  '2025-07-07 16:45:00',  40, -10, 30, NULL,         NULL,      '鈴木一郎', 'LOT-P-003', '製造出庫',       0, '2025-07-07 16:46:00', '2025-07-07 16:46:00'),
  (8, 6, 'ADJUSTMENT',      '2025-07-08 17:59:00', 100,  -5, 95, NULL,         NULL,      '小林直樹', NULL,         '棚卸調整',       0, '2025-07-08 18:00:00', '2025-07-08 18:00:00');

--
-- Table structure for table `products_stock_history`
--

DROP TABLE IF EXISTS products_stock_history;

CREATE TABLE products_stock_history (
    history_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主キー、履歴ID',
    stock_id INT NOT NULL COMMENT '製品在庫ID',
    transaction_type VARCHAR(20) NOT NULL COMMENT '取引種別',
    transaction_date DATETIME NOT NULL COMMENT '処理日時',
    amount_before INT NOT NULL COMMENT '処理前数量',
    amount_change INT NOT NULL COMMENT '変動数量',
    amount_after INT NOT NULL COMMENT '処理後数量',
    customer_name VARCHAR(100) NULL COMMENT '顧客企業名',
    order_no VARCHAR(50) NULL COMMENT '受注番号',
    operator_name VARCHAR(50) NULL COMMENT '処理担当者名',
    manufacturing_lot VARCHAR(50) NULL COMMENT '製造ロット番号',
    shipping_address TEXT NULL COMMENT '出荷先住所',
    shipping_date DATE NULL COMMENT '出荷予定日',
    remarks VARCHAR(255) NULL COMMENT '備考',
    delete_flag TINYINT(1) NOT NULL DEFAULT 0 COMMENT '論理削除フラグ',
    create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時',
    update_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    CONSTRAINT fk_products_stock_history_stock FOREIGN KEY (stock_id) REFERENCES products_stock(stock_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='製品入出荷履歴テーブル';

INSERT INTO products_stock_history VALUES
  (1, 1, 'PRODUCTION_IN',   '2025-07-01 09:15:00',   0,  10, 10, NULL,    NULL,      '山本一郎', 'LOT-M-001', NULL,          NULL,    '初回製造入庫',     0, '2025-07-01 09:16:00', '2025-07-01 09:16:00'),
  (2, 1, 'SHIPMENT',        '2025-07-02 10:20:00',  10,  -3,  7, '企業X', 'SO2001',  '佐々木花子', NULL,        '東京都千代田区1-1-1', '2025-07-03', '出荷',    0, '2025-07-02 10:21:00', '2025-07-02 10:21:00'),
  (3, 2, 'ADJUSTMENT',      '2025-07-03 11:30:00',  20,   2, 22, NULL,    NULL,      '鈴木次郎', NULL,        NULL,          NULL,    '在庫調整',       0, '2025-07-03 11:31:00', '2025-07-03 11:31:00'),
  (4, 2, 'SHIPMENT_CANCEL', '2025-07-04 12:45:00',  22,   0, 22, '企業Y', 'SO2002',  '田中美香', NULL,        NULL,          NULL,    '出荷キャンセル',   0, '2025-07-04 12:46:00', '2025-07-04 12:46:00'),
  (5, 3, 'PRODUCTION_IN',   '2025-07-05 13:55:00',   5,  15, 20, NULL,    NULL,      '小林誠',   'LOT-M-002', NULL,          NULL,    '増産入庫',       0, '2025-07-05 13:56:00', '2025-07-05 13:56:00'),
  (6, 4, 'ADJUSTMENT',      '2025-07-06 14:10:00',  30,  -5, 25, NULL,    NULL,      '渡辺花子', NULL,        NULL,          NULL,    '棚卸差異調整',   0, '2025-07-06 14:11:00', '2025-07-06 14:11:00'),
  (7, 5, 'SHIPMENT',        '2025-07-07 15:35:00',  75, -10, 65, '企業Z', 'SO3001',  '清水花子', NULL,        '大阪市中央区3-4-5', '2025-07-08', '定期出荷',      0, '2025-07-07 15:36:00', '2025-07-07 15:36:00'),
  (8, 3, 'SHIPMENT_CANCEL', '2025-07-08 16:50:00',  20,   0, 20, '企業A', 'SO3002',  '松本太郎', NULL,        NULL,          NULL,    '出荷取消',       0, '2025-07-08 16:51:00', '2025-07-08 16:51:00');