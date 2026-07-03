package com.coding.ai.chatclient;

import com.coding.ai.chatclient.service.AIChatService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ai.document.Document;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
class ChatclientApplicationTests {
	@Autowired
	private AIChatService aiChatService;

	@Test
	void contextLoads() {
	}

	//@Test
	void testIngestDataToVectorStore(){
		String textSample="Let's see how vector stores this text.";
		log.info("passing text-{}", textSample);
		aiChatService.ingestDataToVectorStore(textSample);
		log.info("---method for ingestion called---");
	}

	//running this test will ingest the data in vector store
	//@Test
	void testInsertDocumentsToVectorStore(){
		Document document1 = new Document(
				"""
                Users may receive a "Cannot connect to VPN" error after changing their Windows password.
                The VPN client may still be using cached credentials. Open Windows Credential Manager,
                remove the stored VPN credentials, restart the VPN client, and sign in again using the
                updated password. If the issue persists, verify that the VPN server is reachable.
                """,
				Map.of(
						"id", "KB-001",
						"category", "Networking",
						"subCategory", "VPN",
						"priority", "High",
						"product", "Windows 11",
						"department", "IT",
						"difficulty", "Beginner",
						"tags", List.of("vpn", "credentials", "password", "windows", "remote-access"),
						"author", "Alice",
						"lastUpdated", "2026-05-11"
				)
		);

		Document document2 = new Document(
				"""
                Outlook search may return incomplete or missing results when the Windows search index
                becomes corrupted. Open Indexing Options, rebuild the search index, and allow indexing
                to finish completely. Restart Outlook afterward. Ensure Cached Exchange Mode is enabled
                for the mailbox to improve search performance.
                """,
				Map.of(
						"id", "KB-002",
						"category", "Email",
						"subCategory", "Outlook",
						"priority", "Medium",
						"product", "Outlook 365",
						"department", "IT",
						"difficulty", "Beginner",
						"tags", List.of("outlook", "search", "email", "indexing", "office365"),
						"author", "Bob",
						"lastUpdated", "2026-04-19"
				)
		);

		Document document3 = new Document(
				"""
                A network printer appearing offline usually indicates connectivity or configuration issues.
                Verify the printer's IP address, ensure it is connected to the network, restart the Print
                Spooler service, and remove and re-add the printer if its IP address has changed. Printing
                a test page can confirm successful communication.
                """,
				Map.of(
						"id", "KB-003",
						"category", "Printer",
						"subCategory", "Network Printer",
						"priority", "Medium",
						"product", "HP LaserJet",
						"department", "IT",
						"difficulty", "Beginner",
						"tags", List.of("printer", "offline", "network", "spooler", "hp"),
						"author", "Alice",
						"lastUpdated", "2026-05-01"
				)
		);

		Document document4 = new Document(
				"""
                Users who stop receiving multi-factor authentication prompts may have an outdated device
                registration. Remove the registered authenticator device from the identity provider,
                register the device again using Microsoft Authenticator, and verify that time synchronization
                and notification permissions are enabled on the mobile device.
                """,
				Map.of(
						"id", "KB-004",
						"category", "Security",
						"subCategory", "Multi-Factor Authentication",
						"priority", "High",
						"product", "Microsoft Entra ID",
						"department", "Security",
						"difficulty", "Intermediate",
						"tags", List.of("mfa", "authentication", "security", "authenticator", "login"),
						"author", "Carol",
						"lastUpdated", "2026-03-28"
				)
		);

		Document document5 = new Document(
				"""
                OneDrive synchronization issues are commonly caused by insufficient cloud storage,
                unsupported file names, or network interruptions. Check available storage space,
                restart the OneDrive sync client, rename files containing unsupported characters,
                and verify that synchronization is not paused. Review the sync status for detailed errors.
                """,
				Map.of(
						"id", "KB-005",
						"category", "Storage",
						"subCategory", "Cloud Sync",
						"priority", "Low",
						"product", "Microsoft OneDrive",
						"department", "IT",
						"difficulty", "Beginner",
						"tags", List.of("onedrive", "sync", "cloud", "files", "storage"),
						"author", "David",
						"lastUpdated", "2026-06-02"
				)
		);

		Document document6 = new Document(
				"""
                Slow Wi-Fi performance is often caused by wireless interference, outdated network drivers,
                or poor signal strength. Restart the wireless router, connect to the 5 GHz band if available,
                update the Wi-Fi adapter driver, and reduce interference from nearby electronic devices.
                Running a speed test before and after these changes can help verify improvements.
                """,
				Map.of(
						"id", "KB-006",
						"category", "Networking",
						"subCategory", "Wi-Fi",
						"priority", "High",
						"product", "Wireless Network",
						"department", "IT",
						"difficulty", "Beginner",
						"tags", List.of("wifi", "internet", "network", "slow", "router"),
						"author", "Emily",
						"lastUpdated", "2026-04-15"
				)
		);

		Document document7 = new Document(
				"""
                Laptop batteries that discharge unusually fast may be affected by excessive background
                applications, high screen brightness, or battery degradation. Close unnecessary programs,
                reduce display brightness, enable battery saver mode, and update BIOS and chipset drivers.
                Battery health reports can help determine whether replacement is necessary.
                """,
				Map.of(
						"id", "KB-007",
						"category", "Hardware",
						"subCategory", "Battery",
						"priority", "Medium",
						"product", "Windows Laptop",
						"department", "IT",
						"difficulty", "Beginner",
						"tags", List.of("battery", "laptop", "power", "performance", "windows"),
						"author", "Frank",
						"lastUpdated", "2026-05-20"
				)
		);

		Document document8 = new Document(
				"""
                PostgreSQL refusing incoming connections may indicate that the database service is stopped,
                the pg_hba.conf file is incorrectly configured, or firewall rules are blocking the default
                database port. Verify the PostgreSQL service status, review authentication rules,
                and ensure port 5432 is accessible from client machines.
                """,
				Map.of(
						"id", "KB-008",
						"category", "Database",
						"subCategory", "PostgreSQL",
						"priority", "High",
						"product", "PostgreSQL",
						"department", "Database",
						"difficulty", "Intermediate",
						"tags", List.of("postgresql", "database", "connection", "pg_hba", "firewall"),
						"author", "Grace",
						"lastUpdated", "2026-06-10"
				)
		);

		Document document9 = new Document(
				"""
                Docker containers that stop immediately after starting usually indicate that the primary
                application process has exited. Inspect the container logs, verify the ENTRYPOINT and CMD
                instructions in the Docker image, and ensure required environment variables and configuration
                files are available before startup.
                """,
				Map.of(
						"id", "KB-009",
						"category", "Containers",
						"subCategory", "Docker",
						"priority", "Medium",
						"product", "Docker Engine",
						"department", "DevOps",
						"difficulty", "Intermediate",
						"tags", List.of("docker", "container", "logs", "entrypoint", "startup"),
						"author", "Henry",
						"lastUpdated", "2026-05-30"
				)
		);

		Document document10 = new Document(
				"""
                Kubernetes Pods remaining in the Pending state usually indicate insufficient cluster
                resources, unsatisfied node selectors, missing Persistent Volume Claims, or scheduling
                constraints. Use kubectl describe pod to inspect scheduling events and verify that
                worker nodes have adequate CPU, memory, and storage resources available.
                """,
				Map.of(
						"id", "KB-010",
						"category", "Containers",
						"subCategory", "Kubernetes",
						"priority", "High",
						"product", "Kubernetes",
						"department", "DevOps",
						"difficulty", "Advanced",
						"tags", List.of("kubernetes", "pod", "pending", "scheduler", "cluster"),
						"author", "Irene",
						"lastUpdated", "2026-06-14"
				)
		);

		Document document11 = new Document(
				"""
                Git merge conflicts occur when multiple branches modify the same section of a file
                and Git cannot determine which version should be kept automatically. Review the
                conflicting files, manually resolve the differences, remove conflict markers,
                test the application, and complete the merge with a new commit. Frequent commits
                and regularly pulling changes from the main branch can reduce merge conflicts.
                """,
				Map.of(
						"id", "KB-011",
						"category", "Version Control",
						"subCategory", "Git",
						"priority", "Medium",
						"product", "Git",
						"department", "Software Development",
						"difficulty", "Beginner",
						"tags", List.of("git", "merge", "conflict", "branch", "repository"),
						"author", "Jack",
						"lastUpdated", "2026-05-18"
				)
		);

		Document document12 = new Document(
				"""
                Java applications throwing OutOfMemoryError often indicate memory leaks, excessive
                object creation, or insufficient heap allocation. Analyze heap dumps using memory
                profiling tools, identify objects that are not being garbage collected, optimize
                object lifecycles, and increase JVM heap size only after confirming that the
                application genuinely requires additional memory.
                """,
				Map.of(
						"id", "KB-012",
						"category", "Java",
						"subCategory", "Memory Management",
						"priority", "High",
						"product", "OpenJDK",
						"department", "Software Development",
						"difficulty", "Intermediate",
						"tags", List.of("java", "heap", "memory", "garbage-collection", "outofmemory"),
						"author", "Karen",
						"lastUpdated", "2026-06-08"
				)
		);

		Document document13 = new Document(
				"""
                Optimistic locking prevents multiple users from accidentally overwriting each
                other's changes during concurrent updates. A version column is checked before
                committing a transaction. If another transaction has already modified the same
                record, the update fails with an optimistic locking exception, allowing the
                application to retry or notify the user about the conflict.
                """,
				Map.of(
						"id", "KB-013",
						"category", "Spring Boot",
						"subCategory", "Transactions",
						"priority", "High",
						"product", "Spring Data JPA",
						"department", "Software Development",
						"difficulty", "Advanced",
						"tags", List.of("spring", "jpa", "optimistic-locking", "transaction", "version"),
						"author", "Laura",
						"lastUpdated", "2026-06-12"
				)
		);

		Document document14 = new Document(
				"""
                Linux 'Permission denied' errors usually occur because the current user lacks
                the required read, write, or execute permissions. Verify file ownership using
                ls -l, update permissions with chmod if appropriate, change ownership using
                chown when necessary, and avoid running applications with elevated privileges
                unless absolutely required.
                """,
				Map.of(
						"id", "KB-014",
						"category", "Linux",
						"subCategory", "File Permissions",
						"priority", "Medium",
						"product", "Ubuntu Linux",
						"department", "System Administration",
						"difficulty", "Beginner",
						"tags", List.of("linux", "permissions", "chmod", "chown", "security"),
						"author", "Michael",
						"lastUpdated", "2026-04-25"
				)
		);

		Document document15 = new Document(
				"""
                A 401 Unauthorized response indicates that the client request lacks valid
                authentication credentials. Verify that the Authorization header is present,
                ensure the access token has not expired, confirm the authentication scheme
                matches the API requirements, and check whether the user has permission to
                access the requested resource.
                """,
				Map.of(
						"id", "KB-015",
						"category", "Web APIs",
						"subCategory", "Authentication",
						"priority", "High",
						"product", "REST API",
						"department", "Software Development",
						"difficulty", "Beginner",
						"tags", List.of("api", "401", "authentication", "token", "authorization"),
						"author", "Nancy",
						"lastUpdated", "2026-06-20"
				)
		);

		Document document16 = new Document(
				"""
                Redis cache misses can significantly increase database load and application response time.
                Verify that frequently accessed data is actually being cached, review cache expiration times,
                choose an appropriate eviction policy such as LRU or LFU, and monitor cache hit ratios.
                If memory usage is consistently high, consider increasing available memory or optimizing
                the size of cached objects.
                """,
				Map.of(
						"id", "KB-016",
						"category", "Caching",
						"subCategory", "Redis",
						"priority", "High",
						"product", "Redis",
						"department", "Software Development",
						"difficulty", "Intermediate",
						"tags", List.of("redis", "cache", "performance", "eviction", "memory"),
						"author", "Oliver",
						"lastUpdated", "2026-06-18"
				)
		);

		Document document17 = new Document(
				"""
                Grafana dashboards displaying no metrics usually indicate problems with the configured
                data source or metric collection. Verify that Prometheus is reachable, confirm that the
                correct data source is selected, check scrape targets for errors, and ensure the dashboard
                queries match the available metric names. Reviewing Prometheus targets is often the first
                step in troubleshooting missing metrics.
                """,
				Map.of(
						"id", "KB-017",
						"category", "Monitoring",
						"subCategory", "Grafana",
						"priority", "Medium",
						"product", "Grafana",
						"department", "DevOps",
						"difficulty", "Intermediate",
						"tags", List.of("grafana", "prometheus", "monitoring", "metrics", "dashboard"),
						"author", "Sophia",
						"lastUpdated", "2026-05-27"
				)
		);

		Document document18 = new Document(
				"""
                Slow Elasticsearch queries are commonly caused by oversized shards, expensive wildcard
                searches, insufficient JVM memory, or poorly designed mappings. Review query execution
                using the Profile API, optimize index mappings, reduce wildcard usage, and distribute
                shard sizes appropriately to improve search performance.
                """,
				Map.of(
						"id", "KB-018",
						"category", "Search Engine",
						"subCategory", "Elasticsearch",
						"priority", "High",
						"product", "Elasticsearch",
						"department", "Platform Engineering",
						"difficulty", "Advanced",
						"tags", List.of("elasticsearch", "search", "performance", "shards", "query"),
						"author", "William",
						"lastUpdated", "2026-06-22"
				)
		);

		Document document19 = new Document(
				"""
                Expired JWT access tokens should result in a 401 Unauthorized response from the server.
                Applications can use refresh tokens to obtain new access tokens without requiring users
                to authenticate again. Always validate the token signature, expiration time, issuer,
                and audience before granting access to protected resources.
                """,
				Map.of(
						"id", "KB-019",
						"category", "Security",
						"subCategory", "JWT Authentication",
						"priority", "High",
						"product", "JSON Web Token",
						"department", "Software Development",
						"difficulty", "Intermediate",
						"tags", List.of("jwt", "authentication", "token", "security", "oauth"),
						"author", "Emma",
						"lastUpdated", "2026-06-25"
				)
		);

		Document document20 = new Document(
				"""
                Jenkins pipeline failures caused by missing environment variables often occur after
                migrating jobs or updating credentials. Verify that required variables are defined
                in the pipeline configuration, ensure credentials are correctly referenced using
                Jenkins Credentials, and review build logs to identify which variable is missing.
                Shared libraries and global pipeline settings should also be validated.
                """,
				Map.of(
						"id", "KB-020",
						"category", "CI/CD",
						"subCategory", "Jenkins",
						"priority", "Medium",
						"product", "Jenkins",
						"department", "DevOps",
						"difficulty", "Intermediate",
						"tags", List.of("jenkins", "pipeline", "cicd", "credentials", "environment"),
						"author", "Daniel",
						"lastUpdated", "2026-06-30"
				)
		);

		List<Document> documents = List.of(
				document1,document2,document3,document4,document5,
				document6,document7,document8,document9,document10,
				document11,document12,document13,document14,document15,
				document16,document17,document18,document19,document20);

		aiChatService.ingestDataSetToVectorStore(documents);
	}
}
