package ncu.im3069.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
 

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/FileUploadHandler")
public class FileUploadHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;
     
    // 上傳檔案儲存目錄
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // 上傳配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
 
    /**
     * 上傳資料及儲存檔案
     */
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
        // 檢測是否為多媒體上傳
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是則停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表單必須包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }
 
        // 配置上傳引數
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 設定記憶體臨界值 - 超過後將產生臨時檔案並儲存於臨時目錄中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 設定臨時儲存目錄
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // 設定最大檔案上傳值
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // 設定最大請求值 (包含檔案和表單資料)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文處理
        upload.setHeaderEncoding("UTF-8"); 

        // 構造臨時路徑來儲存上傳的檔案
        // 這個路徑相對當前應用的目錄
        String uploadPath = request.getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;
       
         
        // 如果目錄不存在則建立
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
 
        try {
            // 解析請求的內容選取檔案資料
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
 
            if (formItems != null && formItems.size() > 0) {
                // 迭代表單資料
                for (FileItem item : formItems) {
                    // 處理不在表單中的欄位
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // 在主控臺輸出檔案的上傳路徑
                        System.out.println(filePath);
                        // 儲存檔案到硬碟
                        item.write(storeFile);
                        request.setAttribute("message",
                            "檔案上傳成功!");
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message",
                    "錯誤訊息: " + ex.getMessage());
        }
        // 跳轉到 message.jsp
        request.getServletContext().getRequestDispatcher("/message.jsp").forward(
                request, response);
    }
}