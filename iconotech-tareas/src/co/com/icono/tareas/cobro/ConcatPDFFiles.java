package co.com.icono.tareas.cobro;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.SimpleBookmark;

public class ConcatPDFFiles {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			int pageOffset = 0;
			ArrayList master = new ArrayList();
			String outFile = "output/notificacion_mandamiento/Combined.pdf";
			Document document = null;
			PdfCopy writer = null;

			String folder = "output/notificacion_mandamiento/1/";
			File _folder = new File(folder);
			File[] filesInFolder;
			filesInFolder = _folder.listFiles();
			boolean start = true;
			for (File file : filesInFolder) {
				PdfReader reader = new PdfReader(file.toString());
				reader.consolidateNamedDestinations();
				int n = reader.getNumberOfPages();
				List bookmarks = SimpleBookmark.getBookmark(reader);
				if (bookmarks != null) {
					if (pageOffset != 0) {
						SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset, null);
					}
					master.addAll(bookmarks);
				}
				pageOffset += n;

				if (start) {
					document = new Document(reader.getPageSizeWithRotation(1));
					writer = new PdfCopy(document, new FileOutputStream(outFile));
					document.open();
					start = false;
				}
				PdfImportedPage page;
				for (int i = 0; i < n;) {
					++i;
					page = writer.getImportedPage(reader, i);
					writer.addPage(page);
				}
				PRAcroForm form = reader.getAcroForm();
				if (form != null) {
					writer.copyAcroForm(reader);
				}
			}

			if (!master.isEmpty()) {
				writer.setOutlines(master);
			}
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}